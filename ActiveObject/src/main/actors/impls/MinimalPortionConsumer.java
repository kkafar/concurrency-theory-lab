package main.actors.impls;

import main.actors.interfaces.Consumer;
import main.ao.client.interfaces.BufferProxy;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.Buffer;

public final class MinimalPortionConsumer extends Consumer {
  public MinimalPortionConsumer(BufferProxy buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return minPortionSize;
  }
}
