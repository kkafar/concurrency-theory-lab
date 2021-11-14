package main.actors.impl;

import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;

public final class MaximumPortionProducer extends Producer {
  public MaximumPortionProducer(Buffer buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return maxPortionSize;
  }
}
