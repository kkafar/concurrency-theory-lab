package main.common.requests;

public class Request {
  private final RequestType mType;
  private final int mPortionSize;

  public Request(final RequestType type, final int portionSize) {
    mType = type;
    mPortionSize = portionSize;
  }

  public RequestType getRequestType() {
    return mType;
  }

  public int getPortionSize() {
    return mPortionSize;
  }
}
