package main.actors.interfaces;

import main.ao.struct.interfaces.Promise;
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

  abstract public void take(final int requestSize);
}
