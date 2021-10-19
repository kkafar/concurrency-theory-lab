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

  public SharedBuffer(int baseSize) {
    items = new Object[2 * baseSize];
    firstEmptyIndex = firstOccupied = objectCount = 0;
  }

  public void put(Object[] objects) throws InterruptedException {
    if (objects.length == 0) return;
    lock.lock();
    try {
      while (objectCount + objects.length > items.length)
        notFull.await();

      putNoChecks(objects);
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

  public Object[] take(int n) throws InterruptedException {
    if (n <= 0) throw new IllegalArgumentException("n parameter MUST be > 0");
    lock.lock();
    try {
      while (objectCount < n)
        notEmpty.await();

      Object[] rt = takeNoChecks(n);
      notFull.signal();
      return rt;
    } finally {
      lock.unlock();
    }
  }

  private void putNoChecks(Object[] objects) {
    for (Object object : objects) {
      items[firstEmptyIndex++] = object;
      firstEmptyIndex %= items.length;
    }
    objectCount += objects.length;
  }

  private Object[] takeNoChecks(int n) {
    Object[] rt = new Object[n];
    for (int i = 0; i < n; ++i) {
      rt[i] = items[firstOccupied++];
      firstOccupied %= items.length;
    }
    objectCount -= n;
    return rt;
  }
}
