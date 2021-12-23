package main.actors;

import main.common.messages.RequestType;

public class Consumer extends Client {
  public Consumer(PortionGeneratorFactory portionGeneratorFactory) {
    super(portionGeneratorFactory);
  }

  @Override
  protected RequestType getRequestType() {
    return RequestType.CONSUME;
  }
}
