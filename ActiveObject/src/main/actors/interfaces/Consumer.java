package main.actors.interfaces;

import main.ao.client.interfaces.BufferProxy;
import main.ao.struct.impls.Promise;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;

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
    System.out.println("TAKE");
    Promise<Object[]> promise = buffer.take(n);
    while (promise.isNotConsumed()) {
//      System.out.println("Running extra work in Consumer");
      extraWork.run();
    }
    if (promise.isResolved()) {
      System.out.println("RESOLVED CONSUMER");
    } else if (promise.isRejected()) {
      System.out.println("REJECTED CONSUMER");
      deactivate();
    } else {
      System.out.println("UNDEFINED CONSUMER");
    }
    if (active) ++completedOperations;
  }
}
