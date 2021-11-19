package main.buffer.interfaces;

import java.util.LinkedList;
import java.util.List;

abstract public class BoundedSizeBufferWithOpsLimit extends BoundedBuffer {
  protected final long maxOperations;
  protected long completedOperations;
  protected boolean operationLimitReached;

  protected final List<BufferOpsLimitReachedListener> listeners;

  public BoundedSizeBufferWithOpsLimit(final int bufferSize, final long maxOperations) {
    super(bufferSize);
    this.maxOperations = maxOperations;
    this.completedOperations = 0;
    this.listeners = new LinkedList<>();
    this.operationLimitReached = false;
  }

  public void block() {
    operationLimitReached = true;
    notifyOnOpsLimitReached();
  }

  public boolean isBlocked() {
    return operationLimitReached;
  }

  public void registerBufferOpsLimitReachedListener(BufferOpsLimitReachedListener listener) {
    listeners.add(listener);
  }

  public void notifyOnOpsLimitReached() {
//    listeners.forEach(BufferOpsLimitReachedListener::notifyOnOpsLimitReached);
  }
}
