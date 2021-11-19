package main.actors.interfaces;

import main.ao.client.interfaces.BufferProxy;
import main.ao.struct.impls.Promise;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.utils.UnitOfWork;
import java.util.Arrays;

abstract public class Producer extends Actor {
  public Producer(BufferProxy buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  public void run() {

    this.active = true;
    while (active) {
      Object[] input = new Object[getNextPortionSize()];
      Arrays.fill(input, new Object());
      put(input);
    }
  }

  public void put(Object[] objects) {
    System.out.println("PUT");
    Promise<Boolean> promise = buffer.put(objects);
    while (promise.isNotConsumed()) {
//      System.out.println("Running extra work in Producer");
      extraWork.run();
    }
    if (promise.isResolved()) {
      System.out.println("RESOLVED PRODUCER");
    } else if (promise.isRejected()) {
      System.out.println("REJECTED PRODUCER");
      deactivate();
    } else {
      System.out.println("UNDEFINED PRODUCER");
    }
    System.out.flush();
    if (active) ++completedOperations;
  }
}
