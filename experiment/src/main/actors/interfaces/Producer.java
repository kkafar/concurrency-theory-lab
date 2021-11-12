package main.actors.interfaces;

import main.buffer.interfaces.Buffer;

abstract public class Producer extends Actor {
  public Producer(Buffer buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  public abstract void put(Object[] objects);
}
