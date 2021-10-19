package main.tasks.prodcons;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedBuffer {
  private final Lock lock = new ReentrantLock();
  private final Condition notFull  = lock.newCondition();
  private final Condition notEmpty = lock.newCondition();

  private final Object[] items;
  private int firstEmptyIndex, firstOccupied, objectCount;

  public SharedBuffer(int size) {
    items = new Object[size];
    firstEmptyIndex = firstOccupied = objectCount = 0;
  }

  public void put(Object x) throws InterruptedException {
    lock.lock();
    try {
      while (objectCount == items.length)
        notFull.await();
      items[firstEmptyIndex] = x;
      if (++firstEmptyIndex == items.length) firstEmptyIndex = 0;
      ++objectCount;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

  public Object take() throws InterruptedException {
    lock.lock();
    try {
      while (objectCount == 0)
        notEmpty.await();
      Object x = items[firstOccupied];
      if (++firstOccupied == items.length) firstOccupied = 0;
      --objectCount;
      notFull.signal();
      return x;
    } finally {
      lock.unlock();
    }
  }
}
