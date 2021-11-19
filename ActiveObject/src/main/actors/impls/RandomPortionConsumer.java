package main.actors.impls;

import main.actors.interfaces.Consumer;
import main.ao.client.interfaces.BufferProxy;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.Buffer;

public final class RandomPortionConsumer extends Consumer {
  public RandomPortionConsumer(BufferProxy buffer, final long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return rng.nextInt(maxPortionSize - minPortionSize + 1) + minPortionSize;
  }
}
