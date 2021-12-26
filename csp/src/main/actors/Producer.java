package main.actors;

import main.common.HalfDuplexChannel;
import main.common.messages.RequestType;

public class Producer extends Client {
  public Producer(PortionGeneratorFactory portionGeneratorFactory, int id) {
    super(portionGeneratorFactory, id);
  }

  @Override
  protected RequestType getRequestType() {
    return RequestType.PRODUCE;
  }

  @Override
  protected void setDescriptionInOperationCountTracker() {
    mOperationCountTracker.setDescription("Producer " + mID);
  }
}
