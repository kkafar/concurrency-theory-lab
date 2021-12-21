package main.actors;

import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess, CompletedOperationCountTracker {
  private final One2OneChannelInt mCommunicationChannel;
  private int mCompletedOperations;
  private final PortionGenerator mPortionGenerator;

  public Consumer(final One2OneChannelInt in,
                  final PortionGeneratorFactory portionGeneratorFactory
  ) {
    mCommunicationChannel = in;
    mCompletedOperations = 0;
    mPortionGenerator = portionGeneratorFactory.create();
  }

  public void run() {
    System.out.println(mCommunicationChannel.in().read());
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
