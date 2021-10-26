package main.lab;

import java.util.Arrays;

public class Producer implements Runnable {
  private final SynchronizedCyclicBuffer buffer;

  public Producer(SynchronizedCyclicBuffer buffer) {
    this.buffer = buffer;
  }

  @Override
  public void run() {
    try {
      while (true) {
        buffer.put();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
