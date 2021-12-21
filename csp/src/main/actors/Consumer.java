package main.actors;

import org.jcsp.lang.One2OneChannel;

public class Consumer extends Actor {

  public Consumer(final One2OneChannel communicationChannel,
               final PortionGeneratorFactory portionGeneratorFactory
  ) {
    super(communicationChannel, portionGeneratorFactory);
  }

  public void run() {
    System.out.println(mChannel.in().read());
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
