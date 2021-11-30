package main.buffer.interfaces;

abstract public class BoundedBufferWithOpsLimit extends BoundedBuffer {

  protected final long maxOperations;
  protected long completedOperations;
  protected boolean operationLimitReached;

  public BoundedBufferWithOpsLimit(final int bufferSize, final long maxOperations) {
    super(bufferSize);
    this.maxOperations = maxOperations;
    this.completedOperations = 0;
    this.operationLimitReached = false;
  }

  public void block() {
    operationLimitReached = true;
  }

  public boolean isBlocked() {
    return completedOperations >= maxOperations;
  }
}
