package main.buffer.impl;

import main.actors.interfaces.Actor;
import main.buffer.interfaces.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FourCondsBufferProxy implements Buffer {
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition producers = lock.newCondition();
  private final Condition consumers = lock.newCondition();
  private final Condition firstProducer = lock.newCondition();
  private final Condition firstConsumer = lock.newCondition();

  private final CyclicBuffer buffer;

  private boolean firstProducerAwaiting = false;
  private boolean firstConsumerAwaiting = false;

  private final long maxOperations;
  private long completedOperations;

  private boolean actorsSignalled = false;

  public FourCondsBufferProxy(final int size, final long maxOperations, boolean log) {
    buffer = new CyclicBuffer(size, log);
    this.maxOperations = maxOperations;
    this.completedOperations = 0;
  }

  public int getSize() {
    return buffer.getSize();
  }

  public void setLog(final boolean log) {
    this.buffer.setLog(log);
  }

  public void put(final Object[] objects) {
    lock.lock();
    try {
      while (firstProducerAwaiting) {
        producers.await();
        if (completedOperations >= maxOperations) {
          ((Actor) Thread.currentThread()).deactivate();
          signalAllActors();
          return;
        }
      }
      while (!buffer.canPut(objects.length)) {
        firstProducerAwaiting = true;
        firstProducer.await();
        if (completedOperations >= maxOperations) {
          ((Actor) Thread.currentThread()).deactivate();
          signalAllActors();
          return;
        }
        firstProducerAwaiting = false;
      }

      buffer.put(objects);
      ++completedOperations;
      if (completedOperations >= maxOperations) {
        ((Actor) Thread.currentThread()).deactivate();
        signalAllActors();
      }
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      if (firstConsumerAwaiting) {
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
      while (firstConsumerAwaiting) {
        consumers.await();
        if (completedOperations >= maxOperations) {
          ((Actor) Thread.currentThread()).deactivate();
          signalAllActors();
          return null;
        }
      }
      while (!buffer.canTake(n)) {
        firstConsumerAwaiting = true;
        firstConsumer.await();
        if (completedOperations >= maxOperations) {
          ((Actor) Thread.currentThread()).deactivate();
          signalAllActors();
          return null;
        }
        firstConsumerAwaiting = false;
      }
      ++completedOperations;
      return buffer.take(n);
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      if (completedOperations >= maxOperations) {
        ((Actor) Thread.currentThread()).deactivate();
        signalAllActors();
      }
      if (firstProducerAwaiting) {
        firstProducer.signal();
      } else {
        producers.signal();
      }
      lock.unlock();
    }
    return null;
  }

  private void signalAllActors() {
    if (!actorsSignalled) {
      System.out.println("Signalled all actors");
      actorsSignalled = true;
      firstConsumer.signal();
      firstProducer.signal();
      producers.signalAll();
      consumers.signalAll();
    }
  }
}
