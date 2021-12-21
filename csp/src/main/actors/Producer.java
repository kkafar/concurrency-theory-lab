package main.actors;

import org.jcsp.lang.One2OneChannel;

public class Producer extends Actor {

  public Producer(final One2OneChannel communicationChannel,
                  final PortionGeneratorFactory portionGeneratorFactory
  ) {
    super(communicationChannel, portionGeneratorFactory);
  }

  public void run() {
    mChannel.out().write(mPortionGenerator.generatePortion());
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
