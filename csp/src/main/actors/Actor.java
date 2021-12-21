package main.actors;

import main.buffer.Controller;
import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;

abstract public class Actor implements CompletedOperationCountTracker, CSProcess {
  protected int mCompletedOperations;
  protected final PortionGenerator mPortionGenerator;
  protected One2OneChannel mChannel;


  public Actor(final One2OneChannel communicationChannel,
               final PortionGeneratorFactory portionGeneratorFactory
  ) {
    mCompletedOperations = 0;
    mPortionGenerator = portionGeneratorFactory.create();
    mChannel = communicationChannel;
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }

  public One2OneChannel getChannel() {
    return mChannel;
  }

  abstract public void run();
}
