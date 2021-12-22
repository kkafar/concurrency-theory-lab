package main.buffer;

import main.common.HalfDuplexChannel;
import main.common.OperationCountTracker;
import main.common.messages.Confirmation;
import main.common.messages.Notification;
import main.common.messages.OperationStatus;
import main.common.messages.RequestType;
import org.jcsp.lang.*;

public class Buffer implements CSProcess {
  private final int mCapacity;
  private int mCurrentCapacity;
  private HalfDuplexChannel mChannelWithServer;

  private final OperationCountTracker mOperationCountTracker;

  private final int mID;


  public Buffer(final int capacity,
                final int id
  ) {
    mCapacity = capacity;
    mCurrentCapacity = 0;
    mOperationCountTracker = new OperationCountTracker();
    mChannelWithServer = null;
    mID = id;
  }

  public void setServerChannel(HalfDuplexChannel channel) {
    mChannelWithServer = channel;
  }

  @Override
  public void run() {
    assert mChannelWithServer != null;

    Confirmation confirmation = null;
    OperationStatus operationStatus;

    Guard[] guard = {mChannelWithServer.readEndpointFor(this)};
    Alternative alternative = new Alternative(guard);

    while (true) {
      // nasłuchujemy na requesty od serwera
      alternative.select();

      Notification notification = (Notification) mChannelWithServer.readEndpointFor(this).read();

      if (notification.getRequestType() == RequestType.CONSUME) {
        operationStatus = consume(notification.getResources());
      } else {
        operationStatus = produce(notification.getResources());
      }

      confirmation = new Confirmation(operationStatus, mID);

      sendConfirmationToServer(confirmation);
      sendConfirmationToClient(confirmation, notification.getChannel().writeEndpointFor(this));

      mOperationCountTracker.reportOperation(operationStatus);
    }

  }

  private boolean isConsumptionNotPossible(final int resources) {
    return mCurrentCapacity < resources;
  }

  private boolean isProductionNotPossible(final int resources) {
    return mCurrentCapacity + resources > mCapacity;
  }

  private OperationStatus consume(final int resources) {
    System.out.println("consume");
    if (isConsumptionNotPossible(resources)) {
      return OperationStatus.FAILED;
    }
    mCurrentCapacity -= resources;
    return OperationStatus.SUCCEEDED;
  }

  private OperationStatus produce(final int resources) {
    System.out.println("produce");
    if (isProductionNotPossible(resources)) {
      return OperationStatus.FAILED;
    }
    mCurrentCapacity += resources;
    return OperationStatus.SUCCEEDED;
  }

  private void sendConfirmationToClient(Confirmation confirmationForClient, ChannelOutput clientOutput) {
    clientOutput.write(confirmationForClient);
  }
  private void sendConfirmationToServer(Confirmation confirmationForServer) {
    mChannelWithServer.writeEndpointFor(this).write(confirmationForServer);
  }
}
