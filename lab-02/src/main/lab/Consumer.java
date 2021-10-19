package main.lab;

public class Consumer implements Runnable {
  private final SynchronizedBuffer buffer;

  public Consumer(SynchronizedBuffer buffer) {
    this.buffer = buffer;
  }

  @Override
  public void run() {
    try {
      while (true) {
        System.out.println("CONSUMER WANTS TO TAKE ITEM");
        this.buffer.take();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
