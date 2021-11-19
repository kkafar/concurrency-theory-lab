package main.actors.impls;

import main.actors.interfaces.Consumer;
import main.ao.client.interfaces.BufferProxy;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.Buffer;

public final class MaximumPortionConsumer extends Consumer {
  public MaximumPortionConsumer(BufferProxy buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return maxPortionSize;
  }
}
