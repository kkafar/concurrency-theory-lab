package main.actors.interfaces;

import main.buffer.interfaces.Buffer;
import main.utils.UnitOfWork;

import java.util.Random;

abstract public class Actor extends Thread {
  protected final long initialRngSeed;
  protected final Random rng;
  protected final int maxPortionSize;
  protected final int minPortionSize;

  protected final UnitOfWork extraWork = new UnitOfWork(50000);

  volatile protected boolean active;
  protected long completedOperations;

  protected Buffer buffer;

  public Actor(Buffer buffer, final long initialRngSeed) {
    this.buffer = buffer;
    this.initialRngSeed = initialRngSeed;
    this.rng = new Random(initialRngSeed);
    this.maxPortionSize = buffer.getSize() / 2;
    this.minPortionSize = 1;
    this.completedOperations = 0;
  }

  public long getNumberOfCompletedOperations() {
    return completedOperations;
  }

  public void deactivate() {
    active = false;
  }

  abstract protected int getNextPortionSize();
}
