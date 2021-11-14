package main.actors.impl;

import main.actors.interfaces.Consumer;
import main.buffer.interfaces.Buffer;

public class RandomSizePortionConsumer extends Consumer {
  public RandomSizePortionConsumer(Buffer buffer, final long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  public void run() {
    this.active = true;
    while (active) {
      take(getNextPortionSize());
    }
  }

  @Override
  public void take(int n) {
    try {
      buffer.take(n);
      if (active) ++completedOperations;
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }
  }
}
