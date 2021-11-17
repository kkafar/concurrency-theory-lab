package main.actors.impl;

import main.actors.interfaces.Consumer;
import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;

public final class MinimalPortionProducer extends Producer {
  public MinimalPortionProducer(Buffer buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return minPortionSize;
  }
}
