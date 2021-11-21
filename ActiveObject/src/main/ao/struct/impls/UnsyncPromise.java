package main.ao.struct.impls;

import main.ao.struct.interfaces.Promise;



public class UnsyncPromise<T> extends Promise<T> {
  @Override
  public void resolve(T value) {
    if (isNotConsumed()) {
      this.value = value;
      isResolved = true;
    } else {
      throw new IllegalStateException("Promise has already been consumed!");
    }
  }

  @Override
  public void reject() {
    if (isNotConsumed()) {
      isRejected = true;
    } else {
      throw new IllegalStateException("Promise has already been consumed!");
    }
  }

  @Override
  public T getValue() {
      if (isResolved) {
        return value;
     }
      return null;
  }

  @Override
  public boolean isNotConsumed() {
    return !isResolved && !isRejected;
  }

  @Override
  public boolean isConsumed() {
    return isRejected || isResolved;
  }

  @Override
  public boolean isRejected() {
    return isRejected;
  }

  @Override
  public boolean isResolved() {
    return isResolved;
  }
}
