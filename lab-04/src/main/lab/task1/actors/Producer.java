package main.lab.task1.actors;

import main.lab.task1.buffer.SynchronizedCyclicBuffer;
import main.utils.Random;

import java.util.Arrays;

public class Producer extends Thread {
  private final SynchronizedCyclicBuffer buffer;

  public Producer(SynchronizedCyclicBuffer buffer) {
    this.buffer = buffer;
  }

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
