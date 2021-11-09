package main.lab.task1.actors;

import main.lab.task1.buffer.Buffer;
import java.util.Random;


public class Consumer extends Thread {
  private final Buffer buffer;
  private final Random rng;
  private final long iterations;

  public Consumer(Buffer buffer, final long iterations, final long rngSeed) {
    this.buffer = buffer;
    this.rng = new Random(rngSeed);
    this.iterations = iterations;
  }

  public Consumer(Buffer buffer) {
    this(buffer, 100, 1);
  }

  public void run() {
    int halfBufferSize = buffer.getSize() / 2;
    try {
      this.rng.ints(iterations, 1, halfBufferSize).forEach(n -> {
        if (n >= halfBufferSize) --n;
        buffer.take(n);
      });
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
