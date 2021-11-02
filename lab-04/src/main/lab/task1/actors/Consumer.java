package main.lab.task1.actors;

import main.lab.task1.buffer.SynchronizedCyclicBuffer;
import main.utils.Random;

public class Consumer extends Thread {
  private final SynchronizedCyclicBuffer buffer;
  private final int cycles;

  public Consumer(SynchronizedCyclicBuffer buffer, final int cycles) {
    this.buffer = buffer;
    this.cycles = cycles;
  }

  public Consumer(SynchronizedCyclicBuffer buffer) {
    this(buffer, -1);
  }

  public void run() {
    int n;
    int halfBufferSize = buffer.getSize() / 2;
    try {
      while (true) { // TODO: Iterate this.cycles times
        n = Random.getRandomIntInRange(1, halfBufferSize);
        if (n >= halfBufferSize) --n;
        buffer.take(n);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
