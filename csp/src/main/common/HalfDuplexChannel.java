package main.common;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.Channel;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.One2OneChannel;


public class HalfDuplexChannel {
  private final One2OneChannel mMasterToSlave;
  private final One2OneChannel mSlaveToMaster;

  private final Object mMaster, mSlave;


  public HalfDuplexChannel(Object master, Object slave) {
    assert master != null && slave != null;
    mMaster = master;
    mSlave = slave;
    mMasterToSlave = Channel.one2one();
    mSlaveToMaster = Channel.one2one();
  }

  public HalfDuplexChannel(
      Object master,
      One2OneChannel masterToSlave,
      Object slave,
      One2OneChannel slaveToMaster
  ) {
    mMaster = master;
    mSlave = slave;
    mMasterToSlave = masterToSlave;
    mSlaveToMaster = slaveToMaster;
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
