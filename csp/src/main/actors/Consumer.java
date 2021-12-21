package main.actors;

import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess, CompletedOperationCountTracker {
  private final One2OneChannelInt mCommunicationChannel;
  private int mCompletedOperations;

  public Consumer(final One2OneChannelInt in) {
    mCommunicationChannel = in;
    mCompletedOperations = 0;
  }

  public void run() {
    System.out.println(mCommunicationChannel.in().read());
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
