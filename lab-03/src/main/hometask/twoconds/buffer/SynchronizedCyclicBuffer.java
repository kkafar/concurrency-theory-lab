package main.hometask.twoconds.buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedCyclicBuffer {
  private final ReentrantLock lock = new ReentrantLock(true);
  private final Condition producers = lock.newCondition();
  private final Condition consumers = lock.newCondition();

  private final CyclicBuffer buffer;

  public SynchronizedCyclicBuffer(final int size, final int threadPoolCount, boolean log) {
    buffer = new CyclicBuffer(size, threadPoolCount, log);
  }

  public SynchronizedCyclicBuffer(final int size, final int threadPoolCount) {
    this(size,  threadPoolCount, false);
  }

  public int getSize() {
    return buffer.getSize();
  }

  public int[] getStatistics() {
    return buffer.getStatistics();
  }

  public void put(final Object[] objects) {
    lock.lock();
    try {
      while (!buffer.canPut(objects.length)) producers.await();

      buffer.put(objects);
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      consumers.signal();
      lock.unlock();
    }
  }

  public Object[] take(final int n) {
    lock.lock();
    try {
      while (!buffer.canTake(n)) consumers.await();

      return buffer.take(n);
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      producers.signal();
      lock.unlock();
    }
    return null;
  }
}
