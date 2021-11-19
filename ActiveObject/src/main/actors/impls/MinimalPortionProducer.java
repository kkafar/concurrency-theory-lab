package main.actors.impls;

import main.actors.interfaces.Producer;
import main.ao.client.interfaces.BufferProxy;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.Buffer;

public final class MinimalPortionProducer extends Producer {
  public MinimalPortionProducer(BufferProxy buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return minPortionSize;
  }
}
