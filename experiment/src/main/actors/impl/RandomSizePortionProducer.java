package main.actors.impl;

import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;

import java.util.Arrays;

public class RandomSizePortionProducer extends Producer {
  public RandomSizePortionProducer(Buffer buffer, final long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  public void run() {
    this.active = true;
    while (active) {
      Object[] input = new Object[getNextPortionSize()];
      Arrays.fill(input, new Object());
      put(input);
    }
  }


  @Override
  public void put(Object[] objects) {
    buffer.put(objects);
    if (active) ++completedOperations;
  }
}
