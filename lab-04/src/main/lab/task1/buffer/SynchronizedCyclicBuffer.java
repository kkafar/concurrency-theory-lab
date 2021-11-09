package main.lab.task1.buffer;

import main.lab.task1.actors.Consumer;
import main.lab.task1.actors.Producer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedCyclicBuffer implements Buffer {
  private final ReentrantLock sharedLock = new ReentrantLock();
  private final ReentrantLock consumersLock = new ReentrantLock();
  private final ReentrantLock producersLock = new ReentrantLock();
  private final Condition sharedCondition = sharedLock.newCondition();
  private final long maxActions;
  private long actions;

  private final CyclicBuffer buffer;

  public SynchronizedCyclicBuffer(final int size, final long actions, boolean log) {
    buffer = new CyclicBuffer(size, log);
    maxActions = actions;
    this.actions = 0;
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
//      if (actions >= maxActions) {
//        Producer producer = (Producer) Thread.currentThread();
//        producer.deactivate();
//        return;
//      }
      buffer.put(objects);
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
//      ++actions;
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
//      if (actions >= maxActions) {
//        Consumer consumer = (Consumer) Thread.currentThread();
//        consumer.deactivate();
//        return null;
//      }
      return buffer.take(n);
    } finally {
//      ++actions;
      sharedCondition.signal();
      sharedLock.unlock();
      consumersLock.unlock();
    }
  }
}
