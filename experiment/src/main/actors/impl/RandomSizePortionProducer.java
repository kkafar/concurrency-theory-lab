package main.actors.impl;

import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;
import java.util.Arrays;
import java.util.Random;

public class RandomSizePortionProducer extends Producer {
  private final Buffer buffer;
  private final Random rng;
  private final int maxPortionSize;
  private final int minPortionSize;

  public RandomSizePortionProducer(Buffer buffer, final long rngSeed) {
    this.buffer = buffer;
    this.rng = new Random(rngSeed);
    this.maxPortionSize = buffer.getSize() / 2;
    this.minPortionSize = 1;
  }

  public void run() {
    rng.ints(minPortionSize, maxPortionSize).forEach(n -> {
      Object[] input = new Object[n];
      Arrays.fill(input, new Object());;
      put(input);
    });
  }

  @Override
  public void put(Object[] objects) {
    buffer.put(objects);
    ++executedTasks;
  }
}
