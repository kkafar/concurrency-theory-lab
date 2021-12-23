package main.common.messages;

import main.actors.Client;

public class Intent {
  private final RequestType mRequestType;
  private final int mResources;
  private final Client mClient;

  public Intent(
      RequestType requestType,
      int resources,
      Client client
  ) {
    mResources = resources;
    mRequestType = requestType;
    mClient = client;
  }

  public RequestType getRequestType() {
    return mRequestType;
  }

  public int getResources() {
    return mResources;
  }

  public Client getClient() {
    return mClient;
  }
}
