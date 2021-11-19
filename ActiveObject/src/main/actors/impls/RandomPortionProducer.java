package main.actors.impls;

import main.actors.interfaces.Producer;
import main.ao.client.interfaces.BufferProxy;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.Buffer;

public final class RandomPortionProducer extends Producer {
  public RandomPortionProducer(BufferProxy buffer, final long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return rng.nextInt(maxPortionSize - minPortionSize + 1) + minPortionSize;
  }
}
