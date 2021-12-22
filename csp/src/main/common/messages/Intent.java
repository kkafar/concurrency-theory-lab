package main.common.messages;

import main.actors.Actor;

public class Intent {
  private final RequestType mRequestType;
  private final int mResources;
  private final Actor mActor;

  public Intent(
      RequestType requestType,
      int resources,
      Actor actor
  ) {
    mResources = resources;
    mRequestType = requestType;
    mActor = actor;
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
}
