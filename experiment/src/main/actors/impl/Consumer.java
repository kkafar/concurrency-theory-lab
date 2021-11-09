package main.actors.impl;

import main.buffer.interfaces.Buffer;
import java.util.Random;


public class Consumer extends Thread {
  private final Buffer buffer;
  private final Random rng;
  private final long iterations;
  private long executedTasks;

  public Consumer(Buffer buffer, final long iterations, final long rngSeed) {
    this.buffer = buffer;
    this.rng = new Random(rngSeed);
    this.iterations = iterations;
    this.executedTasks = 0;
  }

  public Consumer(Buffer buffer) {
    this(buffer, 100, 1);
  }

  public void run() {
    int halfBufferSize = buffer.getSize() / 2;
    try {
      this.rng.ints(iterations,1, halfBufferSize).forEach(n -> {
        if (n >= halfBufferSize) --n;
        try {
          buffer.take(n);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        ++executedTasks;
      });
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public long getExecutedTasks() {
    return executedTasks;
  }

  public void deactivate() {
    this.interrupt();
  }
}
