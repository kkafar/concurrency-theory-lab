package main.actors.impl;

import main.actors.interfaces.Consumer;
import main.buffer.interfaces.Buffer;
import java.util.Random;


public class RandomSizePortionConsumer extends Consumer {
  private final Buffer buffer;
  private final Random rng;
  private final int maxPortionSize;
  private final int minPortionSize;

  public RandomSizePortionConsumer(Buffer buffer, final long rngSeed) {
    this.buffer = buffer;
    this.rng = new Random(rngSeed);
    this.executedTasks = 0;
    this.maxPortionSize = buffer.getSize() / 2;
    this.minPortionSize = 1;
  }

  public void run() {
    this.rng.ints(minPortionSize, maxPortionSize).forEach(this::take);
  }

  @Override
  public void take(int n) {
    try {
      buffer.take(n);
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      ++executedTasks;
    }
  }
}
