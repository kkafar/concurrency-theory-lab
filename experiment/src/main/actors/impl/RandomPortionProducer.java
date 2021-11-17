package main.actors.impl;

import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;

import java.util.Arrays;

public final class RandomPortionProducer extends Producer {
  public RandomPortionProducer(Buffer buffer, final long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return rng.nextInt(maxPortionSize - minPortionSize + 1) + minPortionSize;
  }
}
