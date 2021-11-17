package main.actors.interfaces;

import main.buffer.interfaces.Buffer;

import java.util.Arrays;

abstract public class Producer extends Actor {
  public Producer(Buffer buffer, long initialRngSeed) {
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
    buffer.put(objects);
    if (active) ++completedOperations;
  }
}
