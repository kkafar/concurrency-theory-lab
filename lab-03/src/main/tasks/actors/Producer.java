package main.tasks.actors;

import main.tasks.buffer.SynchronizedCyclicBuffer;
import main.utils.Random;

import java.util.Arrays;

public class Producer implements Runnable {
  private final SynchronizedCyclicBuffer buffer;

  public Producer(SynchronizedCyclicBuffer buffer) {
    this.buffer = buffer;
  }

  @Override
  public void run() {
    int n;
    int halfBufferSize = buffer.getSize() / 2;
    Object[] input;
    try {
      while (true) {
        n = Random.getRandomIntInRange(1, halfBufferSize);
        if (n >= halfBufferSize) --n;
        input = new Object[n];
        Arrays.fill(input, new Object());
        buffer.put(input);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
