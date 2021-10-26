package main.lab;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedCyclicBuffer {
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition producers = lock.newCondition();
  private final Condition consumers = lock.newCondition();

  private final CyclicBuffer buffer;

  public SynchronizedCyclicBuffer(final int size, boolean log) {
    buffer = new CyclicBuffer(size, log);
  }

  public SynchronizedCyclicBuffer(final int size) {
    this(size, false);
  }

  public int getSize() {
    return buffer.getSize();
  }

  public void put() {
    lock.lock();
    try {
      while (!buffer.canPut()) producers.await();

      buffer.put();
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      consumers.signal();
      lock.unlock();
    }
  }

  public Object take() {
    lock.lock();
    try {
      while (!buffer.canTake()) consumers.await();

      return buffer.take();
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      producers.signal();
      lock.unlock();
    }
    return null;
  }

}
