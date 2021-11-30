package main.actors.impls;

import main.actors.interfaces.Consumer;
import main.ao.client.interfaces.BufferProxy;

public final class MaximumPortionConsumer extends Consumer {
  public MaximumPortionConsumer(BufferProxy buffer, int extraTaskRepeats, long initialRngSeed) {
    super(buffer, extraTaskRepeats,initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return maxPortionSize;
  }
}
