package main.hometask.fourconds.actors;

import main.hometask.fourconds.buffer.SynchronizedCyclicBuffer;
import main.utils.Random;

public class Consumer extends Thread {
  private final SynchronizedCyclicBuffer buffer;
  private final int id;

  public Consumer(SynchronizedCyclicBuffer buffer, final String name, final int id) {
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
