package main.tasks.prodcons;

public class Consumer implements Runnable {
  private final int maxDelay;
  private final SharedBuffer buffer;
  private final int actions;
  private final int maxItems;

  public Consumer(SharedBuffer buffer, int maxItems, int actions, int maxDelay) {
    this.buffer = buffer;
    this.maxDelay = maxDelay;
    this.actions = actions;
    this.maxItems = maxItems;
  }

  public Consumer(SharedBuffer buffer, int maxItems) {
    this(buffer, maxItems, 100, 2000);
  }

  @Override
  public void run() {
    try {
      for (int i = 0; i < actions; ++i){
        Thread.sleep((long) (Math.random() * maxDelay));
        buffer.take((int) (Math.random() * maxItems) + 1);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
