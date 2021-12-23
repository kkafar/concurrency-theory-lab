package main.labtask2.buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedCyclicBuffer {
  private final ReentrantLock lock = new ReentrantLock(true);
  private final Condition producers = lock.newCondition();
  private final Condition consumers = lock.newCondition();
  private final Condition firstProducer = lock.newCondition();
  private final Condition firstConsumer = lock.newCondition();

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

  public void put(final Object[] objects) {
    lock.lock();
    try {
      while (lock.hasWaiters(firstProducer)) {
        producers.await();
      }
      while (!buffer.canPut(objects.length)) {
        firstProducer.await();
      }

      buffer.put(objects);
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      if (lock.hasWaiters(firstConsumer)) {
        firstConsumer.signal();
      } else {
        consumers.signal();
      }
      lock.unlock();
    }
  }

  public Object[] take(final int n) {
    lock.lock();
    try {
      while (lock.hasWaiters(firstConsumer)) {
        consumers.await();
      }
      while (!buffer.canTake(n)) {
        firstConsumer.await();
      }

      return buffer.take(n);
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      if (lock.hasWaiters(firstProducer)) {
        firstProducer.signal();
      } else {
        producers.signal();
      }
      lock.unlock();
    }
    return null;
  }

}
