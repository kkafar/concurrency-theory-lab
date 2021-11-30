package main.actors.impl;

import main.actors.interfaces.Consumer;
import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;

public final class MinimalPortionProducer extends Producer {
  public MinimalPortionProducer(Buffer buffer, final int extraTaskRepeats, long initialRngSeed) {
    super(buffer, extraTaskRepeats, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return minPortionSize;
  }
}
