package main.ao.struct.impls;

public class Promise<T> {
  T value;
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
    }
  }

  public void reject() {
    if (isNotConsumed()) {
      isRejected = true;
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
