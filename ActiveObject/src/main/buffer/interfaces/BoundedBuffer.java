package main.buffer.interfaces;

abstract public class BoundedBuffer extends SyncBuffer {
  protected final int size;

  public BoundedBuffer(final int bufferSize) {
    size = bufferSize;
  }

  @Override
  public int getSize() {
    return size;
  }
}
