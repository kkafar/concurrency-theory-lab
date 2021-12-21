package main.actors;

import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.One2OneChannelInt;

public class Producer extends Actor {

  public Producer(final One2OneChannel communicationChannel,
                  final PortionGeneratorFactory portionGeneratorFactory
  ) {
    super( communicationChannel, portionGeneratorFactory);
  }

  public void run() {
    mConnection.out().write(mPortionGenerator.generatePortion());
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
