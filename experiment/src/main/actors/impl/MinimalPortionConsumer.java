package main.actors.impl;

import main.actors.interfaces.Consumer;
import main.buffer.interfaces.Buffer;

public final class MinimalPortionConsumer extends Consumer {
  public MinimalPortionConsumer(Buffer buffer, final int extraTaskRepeats, long initialRngSeed) {
    super(buffer, extraTaskRepeats, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return minPortionSize;
  }
}
