package main.common.messages;

import main.actors.Actor;
import main.common.HalfDuplexChannel;

public class Notification {
  private final RequestType mRequestType;
  private final int mResources;
  private final Actor mActor;
  private final HalfDuplexChannel mChannel;

  public Notification(
      RequestType requestType,
      int resources,
      Actor actor,
      HalfDuplexChannel channel
  ) {
    mRequestType = requestType;
    mResources = resources;
    mActor = actor;
    mChannel = channel;
  }

  public RequestType getRequestType() {
    return mRequestType;
  }

  public int getResources() {
    return mResources;
  }

  public Actor getActor() {
    return mActor;
  }

  public HalfDuplexChannel getChannel() {
    return mChannel;
  }
}
