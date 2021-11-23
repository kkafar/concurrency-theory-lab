package main.actors.interfaces;

import main.ao.client.interfaces.BufferProxy;
import main.ao.struct.interfaces.Promise;

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
//    System.out.println("PUT");
    Promise<Boolean> promise = buffer.put(objects);
    while (active && promise.isNotConsumed()) {
//      System.out.println("Running extra work in Producer");
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
