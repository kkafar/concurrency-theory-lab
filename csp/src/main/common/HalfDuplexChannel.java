package main.common;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.Channel;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.One2OneChannel;

import java.util.HashMap;
import java.util.Map;

public class HalfDuplexChannel {
  private final One2OneChannel mMasterToSlave;
  private final One2OneChannel mSlaveToMaster;

  private final Object mMaster, mSlave;

//  private final Map<Object, One2OneChannel> map;

  public HalfDuplexChannel(Object master, Object slave) {
    assert master != null && slave != null;
    mMaster = master;
    mSlave = slave;
    mMasterToSlave = Channel.one2one();
    mSlaveToMaster = Channel.one2one();
//    map = Map.of(master, mMasterToSlave, slave, mSlaveToMaster);
  }

  public ChannelOutput writeEndpointFor(Object who) {
    if (who.equals(mMaster)) {
      return mMasterToSlave.out();
    } else if (who.equals(mSlave)) {
      return mSlaveToMaster.out();
    } else {
      throw new IllegalArgumentException("Passed object is neither master or slave!");
    }
  }

  public AltingChannelInput readEndpointFor(Object who) {
    if (who.equals(mMaster)) {
      return mSlaveToMaster.in();
    } else if (who.equals(mSlave)) {
      return mMasterToSlave.in();
    } else {
      throw new IllegalArgumentException("Passed object is neither master or slave!");
    }
  }
}
