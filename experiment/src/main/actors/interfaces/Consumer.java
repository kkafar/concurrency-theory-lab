package main.actors.interfaces;

import main.buffer.interfaces.Buffer;

abstract public class Consumer extends Actor {
  public Consumer(Buffer buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  abstract public void take(final int n) throws InterruptedException;
}
