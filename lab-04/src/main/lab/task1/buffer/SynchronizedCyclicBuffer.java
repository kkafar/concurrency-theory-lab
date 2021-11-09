package main.lab.task1.buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedCyclicBuffer implements Buffer {
  private final ReentrantLock sharedLock = new ReentrantLock();
  private final ReentrantLock consumersLock = new ReentrantLock();
  private final ReentrantLock producersLock = new ReentrantLock();
  private final Condition sharedCondition = sharedLock.newCondition();

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
    producersLock.lock();
    try {
      sharedLock.lock();
      while (!buffer.canPut(objects.length)) {
        sharedCondition.await();
      }
      buffer.put(objects);
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      sharedCondition.signal();
      sharedLock.unlock();
      producersLock.unlock();
    }
  }

  public Object[] take(final int n) {
    consumersLock.lock();
    try {
      sharedLock.lock();
      while (!buffer.canTake(n)) {
        sharedCondition.await();
      }
      return buffer.take(n);
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      sharedCondition.signal();
      sharedLock.unlock();
      consumersLock.unlock();
    }
    return null;
  }
}
