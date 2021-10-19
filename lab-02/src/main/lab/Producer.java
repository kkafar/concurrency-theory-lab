package main.lab;

public class Producer implements Runnable {
  private final SynchronizedBuffer buffer;

  public Producer(SynchronizedBuffer buffer) {
    this.buffer = buffer;
  }

  @Override
  public void run() {
    while (true) {
      try {
        System.out.println("PRODUCER WANTS TO PUT ITEM");
        buffer.put();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
