package main.lab;

import main.lab.SynchronizedCyclicBuffer;

public class Consumer implements Runnable {
  private final SynchronizedCyclicBuffer buffer;
  private final int cycles;

  public Consumer(SynchronizedCyclicBuffer buffer, final int cycles) {
    this.buffer = buffer;
    this.cycles = cycles;
  }

  public Consumer(SynchronizedCyclicBuffer buffer) {
    this(buffer, -1);
  }

  @Override
  public void run() {
    try {
      while (true) { // TODO: Iterate this.cycles times
        buffer.take();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
