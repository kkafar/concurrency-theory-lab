package main.ao.struct.impls;

import java.util.concurrent.locks.ReentrantLock;

public class Promise<T> {
  private T value;
  private boolean isResolved;
  private boolean isRejected;

  public Promise() {
    this.value = null;
    this.isResolved = false;
    this.isRejected = false;
  }
  
  public void resolve(T value) {
    if (isNotConsumed()) {
      this.value = value;
      isResolved = true;
    } else {
      throw new IllegalStateException("Promise has already been consumed!");
    }
  }

  public void reject() {
    if (isNotConsumed()) {
      isRejected = true;
    } else {
      throw new IllegalStateException("Promise has already been consumed!");
    }
  }

  public T getValue() {
      if (isResolved) {
        return value;
      }
      return null;
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
