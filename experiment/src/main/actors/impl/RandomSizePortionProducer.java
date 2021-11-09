package main.actors.impl;

import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;
import java.util.Arrays;
import java.util.Random;

public class RandomSizePortionProducer extends Thread implements Producer {
  private final Buffer buffer;
  private final Random rng;
  private final long iterations;
  private long executedTasks;

  public RandomSizePortionProducer(Buffer buffer, final long iterations, final long rngSeed) {
    this.buffer = buffer;
    this.rng = new Random(rngSeed);
    this.iterations = iterations;
    this.executedTasks = 0;
  }

  public void put() {

  }

  public void run() {
    int halfBufferSize = buffer.getSize() / 2;
    try {
      rng.ints(iterations,1, halfBufferSize).forEach(n -> {
        if (n >= halfBufferSize) --n;
        Object[] input = new Object[n];
        Arrays.fill(input, new Object());;
        buffer.put(input);
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