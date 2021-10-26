package main.labtask2.actors;

import main.labtask2.buffer.SynchronizedCyclicBuffer;
import main.labtask2.utils.Random;

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
    int n;
    int halfBufferSize = buffer.getSize() / 2;
    try {
      while (true) { // TODO: Iterate this.cycles times
        n = Random.getRandomIntInRange(1, halfBufferSize);
        if (n >= halfBufferSize) --n;
//        n = Random.getRandomIntInRange(1, buffer.getSize());
        buffer.take(n);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
