package main.tasks.prodcons;

public class Producer implements Runnable {
  private final int maxDelay;
  private final SharedBuffer buffer;

  public Producer(SharedBuffer buffer, int maxDelay) {
    this.maxDelay = maxDelay;
    this.buffer = buffer;
  }

  public Producer(SharedBuffer buffer) {
    this(buffer, 2000);
  }

  @Override
  public void run() {
    try {
      while(true) {
        Thread.sleep((long)(Math.random() * maxDelay));
        // TODO: write to buffer
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
