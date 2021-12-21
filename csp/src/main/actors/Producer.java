package main.actors;

import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess, CompletedOperationCountTracker {
  private final One2OneChannelInt mCommunicationChannel;
  private int mCompletedOperations;

  public Producer(final One2OneChannelInt out) {
   mCommunicationChannel = out;
   mCompletedOperations = 0;
  }

  public void run() {
    mCommunicationChannel.out().write(((int) (Math.random() * 100) + 1));
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
