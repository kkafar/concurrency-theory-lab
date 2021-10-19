package main.tasks.prodcons;

import java.util.Arrays;

public class Producer implements Runnable {
  private final int maxDelay;
  private final SharedBuffer buffer;
  private final int maxItems;

  public Producer(SharedBuffer buffer, int maxItems, int maxDelay) {
    this.maxDelay = maxDelay;
    this.buffer = buffer;
    this.maxItems = maxItems;
  }

  public Producer(SharedBuffer buffer, int maxItems) {
    this(buffer, maxItems, 2000);
  }

  @Override
  public void run() {
    try {
      for (int i = 0; i < 100; ++i) {
        Thread.sleep((long)(Math.random() * maxDelay));
        Object[] arr = new Object[(int)(Math.random() * maxItems)];
        Arrays.fill(arr, 1);
        buffer.put(arr);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
