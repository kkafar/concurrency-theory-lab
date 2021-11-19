package main.buffer.interfaces;

abstract public class BoundedBuffer extends Buffer {
  protected final int size;

  public BoundedBuffer(final int bufferSize) {
    size = bufferSize;
  }

  /**
   * Get maximum capacity of the buffer
   *
   * @return maximum capacity of the buffer
   */
  public int getSize() {
    return size;
  }
}
