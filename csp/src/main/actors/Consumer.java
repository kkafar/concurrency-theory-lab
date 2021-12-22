package main.actors;

import main.buffer.Buffer;
import main.common.HalfDuplexChannel;
import main.common.messages.RequestType;

public class Consumer extends Actor {
  public Consumer(HalfDuplexChannel channelWithServer, PortionGeneratorFactory portionGeneratorFactory) {
    super(channelWithServer, portionGeneratorFactory);
  }

  @Override
  protected RequestType getRequestType() {
    return RequestType.CONSUME;
  }
}
