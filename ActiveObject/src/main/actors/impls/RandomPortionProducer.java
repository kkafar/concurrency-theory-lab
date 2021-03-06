package main.actors.impls;

import main.actors.interfaces.Producer;
import main.ao.client.interfaces.BufferProxy;

public final class RandomPortionProducer extends Producer {
  public RandomPortionProducer(BufferProxy buffer,int extraTaskRepeats, final long initialRngSeed) {
    super(buffer, extraTaskRepeats, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return rng.nextInt(maxPortionSize - minPortionSize + 1) + minPortionSize;
  }
}
