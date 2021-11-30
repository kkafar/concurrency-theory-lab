package main.actors.interfaces;

import main.buffer.interfaces.Buffer;

abstract public class Consumer extends Actor {
  public Consumer(Buffer buffer, final int extraTaskRepeats, long initialRngSeed) {
    super(buffer, extraTaskRepeats, initialRngSeed);
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
      if (active) {
        ++completedOperations;
        work.run();
      }
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }
  }
}
