package main.actors.impl;

import main.actors.interfaces.Consumer;
import main.buffer.interfaces.Buffer;

public final class RandomPortionConsumer extends Consumer {
  public RandomPortionConsumer(Buffer buffer, final int extraTaskRepeats, final long initialRngSeed) {
    super(buffer, extraTaskRepeats, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return rng.nextInt(maxPortionSize - minPortionSize + 1) + minPortionSize;
  }
}
