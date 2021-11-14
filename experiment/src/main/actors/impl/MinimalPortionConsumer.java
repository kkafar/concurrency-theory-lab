package main.actors.impl;

import main.actors.interfaces.Consumer;
import main.buffer.interfaces.Buffer;

public class MinimalPortionConsumer extends Consumer {
  public MinimalPortionConsumer(Buffer buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  protected int getNextPortionSize() {
    return 1;
  }
}
