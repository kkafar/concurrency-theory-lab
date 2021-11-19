package main.actors.impls;

import main.actors.interfaces.Producer;
import main.ao.client.interfaces.BufferProxy;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.Buffer;

public final class MaximumPortionProducer extends Producer {
  public MaximumPortionProducer(BufferProxy buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return maxPortionSize;
  }
}
