package main.buffer;

public class BufferEntryPair {
  private final Buffer mBuffer;
  private final int mBufferID;

  public BufferEntryPair(Buffer buffer, int ID) {
    mBuffer = buffer;
    mBufferID = ID;
  }

  public Buffer getBuffer() {
    return mBuffer;
  }

  public int getBufferID() {
    return mBufferID;
  }
}
