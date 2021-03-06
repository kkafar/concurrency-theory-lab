package main.actors;

import main.common.OperationCountTracker;
import main.common.HalfDuplexChannel;
import main.common.messages.*;
import org.jcsp.lang.*;

abstract public class Client implements CSProcess {

  protected final PortionGenerator mPortionGenerator;

  protected HalfDuplexChannel mChannelWithServer;
  protected One2OneChannel mOperationChannel;

  protected boolean mOperationPermissionGranted;

  protected OperationCountTracker mOperationCountTracker;

  protected final int mID;

  public Client(PortionGeneratorFactory portionGeneratorFactory, int id) {
    mPortionGenerator = portionGeneratorFactory.create();
    mChannelWithServer = null;
    mOperationPermissionGranted = false;
    mOperationChannel = null;
    mOperationCountTracker = new OperationCountTracker();
    mID = id;
    setDescriptionInOperationCountTracker();
  }

  public void setServerChannel(HalfDuplexChannel channel) {
    mChannelWithServer = channel;
  }

  abstract protected RequestType getRequestType();

  abstract protected void setDescriptionInOperationCountTracker();

  protected boolean isPermissionGranted() {
    return mOperationPermissionGranted;
  }

  protected void consumePermissionGrant() {
    mOperationPermissionGranted = false;
  }

  protected void acquirePermissionGrant() {
    mOperationPermissionGranted = true;
  }

  protected void sendIntentToServer(Intent intent) {
    mChannelWithServer.writeEndpointFor(this).write(intent);
  }

  protected void sendRequestToBuffer(Request request) {
    request.getChannel().writeEndpointFor(this).write(request);
  }

  protected Response awaitServerResponseForIntent() {
    return (Response) mChannelWithServer.readEndpointFor(this).read();
  }

  protected Confirmation awaitBufferConfirmationForRequest(Request request) {
    return ((Confirmation) request.getChannel().readEndpointFor(this).read());
  }

  @Override
  public void run() {
    assert mChannelWithServer != null;

    Intent intent;
    Response serverResponseForIntent = null;

    while (true) { // uzyskanie od serwera pozwolenia na wykonanie operacji
      intent = new Intent(getRequestType(), mPortionGenerator.generatePortion(), this);

      consumePermissionGrant();
      while (!isPermissionGranted()) {
        sendIntentToServer(intent);
        serverResponseForIntent = awaitServerResponseForIntent();

        if (serverResponseForIntent.getIntentStatus() == IntentStatus.REJECTED) {
          mOperationCountTracker.reportRejectedOperation();
        } else if (serverResponseForIntent.getIntentStatus() == IntentStatus.ACCEPTED) {
          acquirePermissionGrant();
        } else {
          throw new IllegalArgumentException("Unknown intent status in server response");
        }
      }

      // wykonanie operacji na buforze
      assert serverResponseForIntent != null : "Null server response";
      Request request = new Request(getRequestType(), serverResponseForIntent.getChannel(), intent.getResources());
      sendRequestToBuffer(request);
      Confirmation confirmation = awaitBufferConfirmationForRequest(request);

      mOperationCountTracker.reportOperation(confirmation.getOperationStatus());

      System.out.println(mOperationCountTracker);
    }
  }

  public OperationCountTracker getOperationCountTracker() {
    return mOperationCountTracker;
  }
}
