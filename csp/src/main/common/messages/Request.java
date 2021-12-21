package main.common.messages;

import org.jcsp.lang.One2OneChannel;

public class Request {
  private final RequestType mType;
  private final int mPortionSize;
  private final One2OneChannel mCommunicationChannel;

  public Request(final RequestType type, One2OneChannel communicationChannel, final int portionSize) {
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

  public One2OneChannel getChannel() {
    return mCommunicationChannel;
  }
}
