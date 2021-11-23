package main.actors.impls;

import main.actors.interfaces.Consumer;
import main.ao.client.interfaces.BufferProxy;

public final class RandomPortionConsumer extends Consumer {
  public RandomPortionConsumer(BufferProxy buffer,int extraTaskRepeats, final long initialRngSeed) {
    super(buffer, extraTaskRepeats, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return rng.nextInt(maxPortionSize - minPortionSize + 1) + minPortionSize;
  }
}
