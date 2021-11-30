package main.ao.struct.interfaces;

abstract public class Promise<T> {
  protected volatile T value;
  protected boolean isResolved;
  protected boolean isRejected;

  public Promise() {
    this.value = null;
    this.isResolved = false;
    this.isRejected = false;
  }

  abstract public T getValue();

  abstract public void resolve(T value);

  abstract public void reject();

  abstract public boolean isNotConsumed();

  abstract public boolean isConsumed();

  abstract public boolean isRejected();

  abstract public boolean isResolved();
}
