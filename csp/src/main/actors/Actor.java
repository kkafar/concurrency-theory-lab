package main.actors;

import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

abstract public class Actor implements CompletedOperationCountTracker, CSProcess {
  protected int mCompletedOperations;
  protected final PortionGenerator mPortionGenerator;
  protected One2OneChannel mConnection;

  public Actor(final One2OneChannel communicationChannel,
               PortionGeneratorFactory portionGeneratorFactory
  ) {
    mCompletedOperations = 0;
    mPortionGenerator = portionGeneratorFactory.create();
    mConnection = communicationChannel;
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }

  abstract public void run();
}
