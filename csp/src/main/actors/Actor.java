package main.actors;

import main.buffer.Controller;
import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.*;

abstract public class Actor implements CompletedOperationCountTracker, CSProcess {
  protected int mCompletedOperations;
  protected final PortionGenerator mPortionGenerator;
  protected final AltingChannelInput mServerInput;
  protected final ChannelOutput mServerOutput;
  protected final AltingChannelInput mBufferInput;
  protected final ChannelOutput mBufferOutput;

  protected boolean mActionPermissionGranted;


  public Actor(final AltingChannelInput serverInput,
               final ChannelOutput serverOutput,
               final AltingChannelInput bufferInput,
               final ChannelOutput bufferOutput,
               final PortionGeneratorFactory portionGeneratorFactory
  ) {
    mCompletedOperations = 0;
    mPortionGenerator = portionGeneratorFactory.create();
    mServerInput = serverInput;
    mServerOutput = serverOutput;
    mBufferInput = bufferInput;
    mBufferOutput = bufferOutput;
    mActionPermissionGranted = false;
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }

//  public ChannelOutput getBufferOutput() {
//    return mBufferOutput;
//  }
//
//  public AltingChannelInput getBufferInput() {
//    return mBufferInput;
//  }
//
//  public ChannelOutput getServerOutput() {
//    return mServerOutput;
//  }

  abstract public void run();
}
