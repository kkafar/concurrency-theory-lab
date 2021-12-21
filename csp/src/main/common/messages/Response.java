package main.common.messages;

import main.buffer.Buffer;
import main.common.HalfDuplexChannel;
import org.jcsp.lang.One2OneChannel;

public class Response {
  private final IntentStatus mIntentStatus;
  private final Buffer mBuffer;
  private final HalfDuplexChannel mChannel;


  public Response(IntentStatus intentStatus, Buffer buffer, HalfDuplexChannel channel) {
    mIntentStatus = intentStatus;
    mBuffer = buffer;
    mChannel = channel;
  }

  public IntentStatus getIntentStatus() {
    return mIntentStatus;
  }

  public Buffer getBuffer() {
    return mBuffer;
  }

  public HalfDuplexChannel getChannel() {
    return mChannel;
  }

}
