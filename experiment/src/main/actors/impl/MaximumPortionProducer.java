package main.actors.impl;

import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;

public final class MaximumPortionProducer extends Producer {
  public MaximumPortionProducer(Buffer buffer, final int extraTaskRepeats, long initialRngSeed) {
    super(buffer, extraTaskRepeats, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return maxPortionSize;
  }
}
