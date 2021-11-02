package main.hometask.twoconds.actors;

import main.hometask.twoconds.buffer.SynchronizedCyclicBuffer;
import main.utils.Random;

import java.util.Arrays;

public class Producer extends Thread {
  private final SynchronizedCyclicBuffer buffer;
  private final int id;

  public Producer(final SynchronizedCyclicBuffer buffer, final String name, final int id) {
    this.buffer = buffer;
    this.setName(name);
    this.id = id;
  }

  public int getID() {
    return id;
  }

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
