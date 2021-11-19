package main.ao.struct.impls;

import java.util.concurrent.locks.ReentrantLock;

public class Promise<T> {
  private final ReentrantLock lock;
  private T value;
  private boolean isResolved;
  private boolean isRejected;

  public Promise() {
    this.value = null;
    this.isResolved = false;
    this.isRejected = false;
    this.lock = new ReentrantLock(true);
  }
  
  public void resolve(T value) {
    lock.lock();
    if (isNotConsumed()) {
      this.value = value;
      System.out.println("Promise resolve");
      isResolved = true;
    } else {
      lock.unlock();
      throw new IllegalStateException("Promise has already been consumed!");
    }
    lock.unlock();
  }

  public void reject() {
    lock.lock();
    if (isNotConsumed()) {
      isRejected = true;
      System.out.println("Promise reject");
    } else {
      lock.unlock();
      throw new IllegalStateException("Promise has already been consumed!");
    }
    lock.unlock();
  }

  public T getValue() {
    lock.lock();
    try {
      if (isResolved) {
        return value;
      }
      return null;
    } finally {
      lock.unlock();
    }
  }

  public boolean isNotConsumed() {
    return !isResolved && !isRejected;
  }

  public boolean isConsumed() {
    return isRejected || isResolved;
  }

  public boolean isRejected() {
    return isRejected;
  }

  public boolean isResolved() {
    return isResolved;
  }
}
