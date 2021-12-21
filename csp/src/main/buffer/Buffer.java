package main.buffer;

import main.common.CompletedOperationCountTracker;
import main.common.messages.Request;
import main.common.messages.RequestType;
import org.jcsp.lang.*;

public class Buffer implements CSProcess, CompletedOperationCountTracker {
  private int mCompletedOperations;
  private final int mCapacity;
  private int mCurrentCapacity;
  private final AltingChannelInput mServerInput;
  private final ChannelOutput mServerOutput;


  public Buffer(final int capacity, final AltingChannelInput serverInput, final ChannelOutput serverOutput) {
    mCompletedOperations = 0;
    mCapacity = capacity;
    mCurrentCapacity = 0;
    mServerInput = serverInput;
    mServerOutput = serverOutput;
  }

  @Override
  public void run() {
    Guard[] guard = {mServerInput};
    Alternative alternative = new Alternative(guard);

    while (true) {
      // nasłuchujemy na requesty od serwera
      alternative.select();

      Request request = (Request) mServerInput.read();

      if (request.getType() == RequestType.CONSUME) {
      }


    }

  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
