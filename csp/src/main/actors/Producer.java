package main.actors;

import main.common.HalfDuplexChannel;
import main.common.messages.RequestType;

public class Producer extends Client {
  public Producer(PortionGeneratorFactory portionGeneratorFactory) {
    super(portionGeneratorFactory);
  }

  @Override
  protected RequestType getRequestType() {
    return RequestType.PRODUCE;
  }
}
