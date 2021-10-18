package main.custom;

public class Monitor {
  private int sharedValue;

  public Monitor(final int initialValue) {
    sharedValue = initialValue;
  }

  public synchronized void modifyValueBy(final int diff) {
    sharedValue += diff;
    sharedValue -= diff;
  }

  public synchronized int getSharedValue() {
    return sharedValue;
  }
}
