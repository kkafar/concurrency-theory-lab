package main.common.messages;

import main.actors.Client;
import main.common.HalfDuplexChannel;

public class Notification {
  private final RequestType mRequestType;
  private final int mResources;
  private final Client mClient;
  private final HalfDuplexChannel mChannel;

  public Notification(
      RequestType requestType,
      int resources,
      Client client,
      HalfDuplexChannel channel
  ) {
    mRequestType = requestType;
    mResources = resources;
    mClient = client;
    mChannel = channel;
  }

  public Notification(Intent intent, HalfDuplexChannel channel) {
    mRequestType = intent.getRequestType();
    mResources = intent.getResources();
    mClient = intent.getClient();
    mChannel = channel;
  }

  public RequestType getRequestType() {
    return mRequestType;
  }

  public int getResources() {
    return mResources;
  }

  public Client getActor() {
    return mClient;
  }

  public HalfDuplexChannel getChannel() {
    return mChannel;
  }
}
