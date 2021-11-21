package main.actors.interfaces;

import main.ao.client.interfaces.BufferProxy;
import main.ao.struct.interfaces.Promise;

abstract public class ProducerAsync extends Producer {
  public ProducerAsync(
      BufferProxy buffer,
      long initialRngSeed
  ) {
    super(buffer, initialRngSeed);
  }

  @Override
  public void put(Object[] objects) {
    Promise<Boolean> promise = (Promise<Boolean>) buffer.put(objects);
    while (active && promise.isNotConsumed()) {
      extraWork.run();
    }
    if (promise.isResolved()) {
      System.out.println("RESOLVED PRODUCER with value: " + promise.getValue());
    } else if (promise.isRejected()) {
      System.out.println("REJECTED PRODUCER");
      deactivate();
    }
    System.out.flush();
    if (active) ++completedOperations;
  }

}
