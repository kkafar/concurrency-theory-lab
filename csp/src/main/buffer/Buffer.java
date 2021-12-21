package main.buffer;

import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

public class Buffer implements CSProcess, CompletedOperationCountTracker {
  private int mCompletedOperations;
  private final int mCapacity;
  private final One2OneChannel mChannel;

  public Buffer(final int capacity, final One2OneChannel channel) {
    mCompletedOperations = 0;
    mCapacity = capacity;
    mChannel = channel;
  }

  @Override
  public void run() {

  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }

  public One2OneChannel getChannel() {
    return mChannel;
  }
}
