package main.buffer.impl;

import main.buffer.interfaces.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeLocksBufferProxy implements Buffer {
  private final ReentrantLock sharedLock = new ReentrantLock();
  private final ReentrantLock consumersLock = new ReentrantLock();
  private final ReentrantLock producersLock = new ReentrantLock();
  private final Condition sharedCondition = sharedLock.newCondition();
  
  private final long maxActions;
  private long actions;

  private final CyclicBuffer buffer;

  public ThreeLocksBufferProxy(final int size, final long actions, boolean log) {
    this.buffer = new CyclicBuffer(size, log);
    this.maxActions = actions;
    this.actions = 0;
  }

  public int getSize() {
    return buffer.getSize();
  }

  public void setLog(final boolean log) {
    this.buffer.setLog(log);
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

  public Object[] take(final int n) throws InterruptedException {
    consumersLock.lock();
    try {
      sharedLock.lock();
      while (!buffer.canTake(n)) {
        sharedCondition.await();
      }
      return buffer.take(n);
    } finally {
      sharedCondition.signal();
      sharedLock.unlock();
      consumersLock.unlock();
    }
  }
}
