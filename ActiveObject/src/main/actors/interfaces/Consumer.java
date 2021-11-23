package main.actors.interfaces;

import main.ao.client.interfaces.BufferProxy;
import main.ao.struct.interfaces.Promise;

abstract public class Consumer extends Actor {
  public Consumer(BufferProxy buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  public void run() {
    this.active = true;
    while (active) {
      take(getNextPortionSize());
    }
  }

  public void take(final int n) {
//    System.out.println("TAKE");
    Promise<Object[]> promise = buffer.take(n);
    while (active && promise.isNotConsumed()) {
      extraWork.run();
    }
    if (promise.isResolved()) {
      System.out.println("RESOLVED PRODUCER");
      ++completedOperations;
    } else if (promise.isRejected()) {
      System.out.println("REJECTED PRODUCER");
      deactivate();
    }
  }
}
