package main.actors;

import main.common.messages.RequestType;

public class Consumer extends Client {
  public Consumer(PortionGeneratorFactory portionGeneratorFactory, int id) {
    super(portionGeneratorFactory, id);
  }

  @Override
  protected RequestType getRequestType() {
    return RequestType.CONSUME;
  }

  @Override
  protected void setDescriptionInOperationCountTracker() {
    mOperationCountTracker.setDescription("Consumer " + mID);
  }
}
