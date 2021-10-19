package main.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomSemaphore {
  private int resourceCount;
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition resourcesAvailable = lock.newCondition();

  public CustomSemaphore(int initialResourceCount) {
    resourceCount = initialResourceCount;
  }

  public CustomSemaphore() {
    this(1);
  }

  public void acquire() throws InterruptedException {
    lock.lock();
    while (resourceCount == 0) resourcesAvailable.await();

    --resourceCount;
  }

  public void release() throws IllegalMonitorStateException {
    if (lock.isHeldByCurrentThread()) {
      ++resourceCount;
      lock.unlock();
      resourcesAvailable.signalAll();
    } else {
      throw new IllegalMonitorStateException("release method called from unauthorized thread");
    }
  }
}
