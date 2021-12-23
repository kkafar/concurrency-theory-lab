package main.common.messages;

import main.common.HalfDuplexChannel;
import org.jcsp.lang.One2OneChannel;

public class Request {
  private final RequestType mType;
  private final int mPortionSize;
  private final HalfDuplexChannel mCommunicationChannel;

  public Request(final RequestType type, HalfDuplexChannel communicationChannel, final int portionSize) {
    mType = type;
    mPortionSize = portionSize;
    mCommunicationChannel = communicationChannel;
  }

  public RequestType getType() {
    return mType;
  }

  public int getPortionSize() {
    return mPortionSize;
  }

  public HalfDuplexChannel getChannel() {
    return mCommunicationChannel;
  }
}
