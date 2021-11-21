package main.actors.interfaces;

import main.ao.client.interfaces.BufferProxy;
import main.ao.struct.interfaces.Promise;

abstract public class ConsumerAsync extends Consumer {
  public ConsumerAsync(BufferProxy buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  public void take(final int requestSize) {
    Promise<Object[]> promise = (Promise<Object[]>) buffer.take(requestSize);
    while (active && promise.isNotConsumed()) {
      extraWork.run();
    }
    if (promise.isResolved()) {
      System.out.println("CONSUMER RESOLVED with value: " + promise.getValue());
    } else if (promise.isRejected()) {
      System.out.println("CONSUMER REJECTED");
      deactivate();
    }
    if (active) ++completedOperations;
  }
}
