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

  public Client(PortionGeneratorFactory portionGeneratorFactory) {
    mPortionGenerator = portionGeneratorFactory.create();
    mChannelWithServer = null;
    mOperationPermissionGranted = false;
    mOperationChannel = null;
    mOperationCountTracker = new OperationCountTracker();
  }

  public void setServerChannel(HalfDuplexChannel channel) {
    mChannelWithServer = channel;
  }



  abstract protected RequestType getRequestType();

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
    // czy nie trzeba wykorzystać jakiejś alternatywy? To będzie zwykła operacja blokująca?
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
      Request request = new Request(getRequestType(), serverResponseForIntent.getChannel(), intent.getResources());
      sendRequestToBuffer(request);
      Confirmation confirmation = awaitBufferConfirmationForRequest(request);

      mOperationCountTracker.reportOperation(confirmation.getOperationStatus());
    }
  }
}
