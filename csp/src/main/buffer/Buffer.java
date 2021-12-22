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
  private final HalfDuplexChannel mChannelWithServer;

  private final OperationCountTracker mOperationCountTracker;


  public Buffer(final int capacity,
                final HalfDuplexChannel channelWithServer
  ) {
    mCapacity = capacity;
    mCurrentCapacity = 0;
    mOperationCountTracker = new OperationCountTracker();
    mChannelWithServer = channelWithServer;
  }

  @Override
  public void run() {
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

      confirmation = new Confirmation(operationStatus);

      sendConfirmationToClient(confirmation, notification.getChannel().writeEndpointFor(this));
      sendConfirmationToServer(confirmation);

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
    if (isConsumptionNotPossible(resources)) {
      return OperationStatus.FAILED;
    }
    mCurrentCapacity -= resources;
    return OperationStatus.SUCCEEDED;
  }

  private OperationStatus produce(final int resources) {
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
