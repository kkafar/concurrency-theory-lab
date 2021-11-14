package main.actors.interfaces;

import main.buffer.interfaces.Buffer;

abstract public class Consumer extends Actor {
  public Consumer(Buffer buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  public void run() {
      this.active = true;
      while (active) {
        take(getNextPortionSize());
      }
  }

  public void take(final int n) {
    try {
      buffer.take(n);
      if (active) ++completedOperations;
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }
  }
}
