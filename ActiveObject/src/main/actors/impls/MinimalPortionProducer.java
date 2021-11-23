package main.actors.impls;

import main.actors.interfaces.Producer;
import main.ao.client.interfaces.BufferProxy;

public final class MinimalPortionProducer extends Producer {
  public MinimalPortionProducer(BufferProxy buffer,int extraTaskRepeats, long initialRngSeed) {
    super(buffer,extraTaskRepeats, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return minPortionSize;
  }
}
