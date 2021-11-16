package main.buffer.impl;

import main.actors.interfaces.Actor;
import main.buffer.interfaces.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class ThreeLocksBufferProxy implements Buffer {
  private final ReentrantLock sharedLock = new ReentrantLock();
  private final ReentrantLock consumersLock = new ReentrantLock();
  private final ReentrantLock producersLock = new ReentrantLock();
  private final Condition sharedCondition = sharedLock.newCondition();

  private final long maxOperations;
  private long completedOperations;
  private boolean actorsSignalled = false;

  private final CyclicBuffer buffer;

  public ThreeLocksBufferProxy(final int size, final long actions, boolean log) {
    this.buffer = new CyclicBuffer(size, log);
    this.maxOperations = actions;
    this.completedOperations = 0;
  }

  public int getSize() {
    return buffer.getSize();
  }

  public void setLog(final boolean log) {
    this.buffer.setLog(log);
  }

  public void put(final Object[] objects) {
    producersLock.lock();
    if (completedOperations >= maxOperations) {
      ((Actor) Thread.currentThread()).deactivate();
      producersLock.unlock();
      return;
    }
    try {
      sharedLock.lock();
      while (!buffer.canPut(objects.length)) {
        sharedCondition.await();
        if (completedOperations >= maxOperations) {
          ((Actor) Thread.currentThread()).deactivate();
          signalAllActors();
          return;
        }
      }
      if (completedOperations >= maxOperations) {
        ((Actor) Thread.currentThread()).deactivate();
        signalAllActors();
        return;
      }
      buffer.put(objects);
      ++completedOperations;
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
    if (completedOperations >= maxOperations) {
      ((Actor) Thread.currentThread()).deactivate();
      consumersLock.unlock();
      return null;
    }
    try {
      sharedLock.lock();
      while (!buffer.canTake(n)) {
        sharedCondition.await();
        if (completedOperations >= maxOperations) {
          ((Actor) Thread.currentThread()).deactivate();
          signalAllActors();
          return null;
        }
      }
      if (completedOperations >= maxOperations) {
        ((Actor) Thread.currentThread()).deactivate();
        signalAllActors();
        return null;
      }
      ++completedOperations;
      return buffer.take(n);
    } finally {
      sharedCondition.signal();
      sharedLock.unlock();
      consumersLock.unlock();
    }
  }

  public void signalAllActors() {
    if (!actorsSignalled) {
      System.out.println("Signalled all actors");
      actorsSignalled = true;
      if (sharedLock.isHeldByCurrentThread()) {
        sharedCondition.signalAll();
      }
    }
  }
}
