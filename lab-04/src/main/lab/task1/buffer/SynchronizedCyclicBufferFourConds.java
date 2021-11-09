package main.lab.task1.buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedCyclicBufferFourConds implements Buffer{
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition producers = lock.newCondition();
  private final Condition consumers = lock.newCondition();
  private final Condition firstProducer = lock.newCondition();
  private final Condition firstConsumer = lock.newCondition();

  private final CyclicBuffer buffer;

  private boolean firstProducerAwaiting = false;
  private boolean firstConsumerAwaiting = false;

  private final long maxActions;
  private long actions;

  public SynchronizedCyclicBufferFourConds(final int size, final long actions, boolean log) {
    buffer = new CyclicBuffer(size, log);
    maxActions = actions;
    this.actions = 0;
  }

  public int getSize() {
    return buffer.getSize();
  }

//  public int[] getStatistics() {
//    return buffer.getStatistics();
//  }

  public void put(final Object[] objects) {
    lock.lock();
    try {
      while (firstProducerAwaiting) {
        producers.await();
      }
      while (!buffer.canPut(objects.length)) {
        firstProducerAwaiting = true;
        firstProducer.await();
        firstProducerAwaiting = false;
      }

      buffer.put(objects);
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
      }
      while (!buffer.canTake(n)) {
        firstConsumerAwaiting = true;
        firstConsumer.await();
        firstConsumerAwaiting = false;
      }

      return buffer.take(n);
    } catch (IllegalMonitorStateException | InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      if (firstProducerAwaiting) {
        firstProducer.signal();
      } else {
        producers.signal();
      }
      lock.unlock();
    }
    return null;
  }
}
