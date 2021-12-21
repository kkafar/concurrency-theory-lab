package main.actors;

import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer extends Actor {

  public Consumer(final One2OneChannel communicationChannel,
                  final PortionGeneratorFactory portionGeneratorFactory
  ) {
    super(communicationChannel, portionGeneratorFactory);
  }

  public void run() {
    System.out.println(mConnection.in().read());
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
