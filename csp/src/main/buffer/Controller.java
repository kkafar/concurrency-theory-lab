package main.buffer;

import main.common.OperationCountTracker;
import main.common.HalfDuplexChannel;
import main.common.messages.*;
import org.jcsp.lang.*;

public class Controller implements CSProcess {
  private HalfDuplexChannel[] mClientChannels;
  private HalfDuplexChannel[] mBufferChannels;

  private int mNumberOfBuffers;
  private int mNumberOfClients;

  private final OperationCountTracker mOperationCountTracker;
  private final BufferSelector mBufferSelector;

  public Controller(
      BufferSelector bufferSelector
  ) {
    mOperationCountTracker = new OperationCountTracker();
    mBufferSelector = bufferSelector;
    mClientChannels = null;
    mBufferChannels = null;
  }

  public void setClientChannels(HalfDuplexChannel[] channels) {
    mClientChannels = channels;
    mNumberOfClients = channels.length;
  }

  public void setBufferChannels(HalfDuplexChannel[] channels) {
    mBufferChannels = channels;
    mNumberOfBuffers = channels.length;
  }

  private AltingChannelInput[] serializeInputs() {
    AltingChannelInput[] inputs = new AltingChannelInput[mNumberOfClients + mNumberOfBuffers];
    for (int i = 0; i < mNumberOfClients; ++i) {
      inputs[i] = mClientChannels[i].readEndpointFor(this);
    }
    for (int i = 0; i < mNumberOfBuffers; ++i) {
      inputs[i + mNumberOfClients] = mBufferChannels[i].readEndpointFor(this);
    }
    return inputs;
  }

  @Override
  public void run() {
    assert mBufferChannels != null && mClientChannels != null;

    int guardIndex;
    BufferEntryPair bufferEntryPair;
    IntentStatus intentStatus;
    Response response;
    Alternative alternative = new Alternative(serializeInputs());

    while (true) {
      guardIndex = alternative.fairSelect();

      if (guardIndex < mNumberOfClients) { // wiadomość od klienta
        int index = guardIndex;

        Intent intent = (Intent) mClientChannels[index].readEndpointFor(this).read();

        bufferEntryPair = mBufferSelector.getBufferForOperation(intent.getRequestType(),
                                                                          intent.getResources());
        if (bufferEntryPair == null) {
          intentStatus = IntentStatus.REJECTED;
          response = new Response(intentStatus, null, null);
        } else {
          intentStatus = IntentStatus.ACCEPTED;
          HalfDuplexChannel clientBufferChannel = new HalfDuplexChannel(
              intent.getClient(),
              bufferEntryPair.getBufferID()
          );
          response = new Response(
              intentStatus,
              bufferEntryPair.getBuffer(),
              clientBufferChannel
          );
          mBufferSelector.lockBuffer(bufferEntryPair.getBufferID());
          sendNotificationToBuffer(new Notification(intent, clientBufferChannel), bufferEntryPair.getBufferID());
        }

        sendResponseToClient(response, index);
      } else { // wiadomość od bufora
        int index = guardIndex - mNumberOfClients;
        Confirmation confirmation = (Confirmation) mBufferChannels[index].readEndpointFor(this).read();

        // Regardless exact operation status, we report that buffer is now free
        mBufferSelector.unlockBuffer(index);

        mOperationCountTracker.reportOperation(confirmation.getOperationStatus());
      }
    }
  }

  private void sendNotificationToBuffer(Notification notification, int bufferID) {
    mBufferChannels[bufferID].writeEndpointFor(this).write(notification);
  }

  private void sendResponseToClient(Response response, int clientID) {
    mClientChannels[clientID].writeEndpointFor(this).write(response);
  }
}
