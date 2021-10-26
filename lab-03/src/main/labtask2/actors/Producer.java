package main.labtask2.actors;

import main.labtask2.buffer.SynchronizedCyclicBuffer;
import main.labtask2.utils.Random;

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
//        n = Random.getRandomIntInRange(1, buffer.getSize());
        input = new Object[n];
        Arrays.fill(input, new Object());
        buffer.put(input);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
