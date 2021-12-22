package main.actors;

import main.buffer.Buffer;
import main.common.HalfDuplexChannel;
import main.common.messages.RequestType;
import main.common.messages.Response;
import main.common.messages.Request;

public class Producer extends Actor {
  public Producer(HalfDuplexChannel channelWithServer, PortionGeneratorFactory portionGeneratorFactory) {
    super(channelWithServer, portionGeneratorFactory);
  }

  @Override
  protected RequestType getRequestType() {
    return RequestType.PRODUCE;
  }
}
