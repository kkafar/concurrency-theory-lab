package main.actors.interfaces;

import main.ao.client.interfaces.BufferProxy;
import main.utils.UnitOfWork;

import java.util.Random;

abstract public class Actor extends Thread {
  protected final long initialRngSeed;
  protected final Random rng;
  protected final int maxPortionSize;
  protected final int minPortionSize;
  protected final int extraTaskRepeats;

  protected final UnitOfWork extraWork;

  volatile protected boolean active;
  protected long completedOperations;

  protected BufferProxy buffer;

  public Actor(BufferProxy buffer, final int extraTaskRepeats, final long initialRngSeed) {
    this.buffer = buffer;
    this.initialRngSeed = initialRngSeed;
    this.rng = new Random(initialRngSeed);
    this.maxPortionSize = buffer.getSize() / 2;
    this.minPortionSize = 1;
    this.completedOperations = 0;
    this.extraTaskRepeats = extraTaskRepeats;
    this.extraWork = new UnitOfWork(extraTaskRepeats);
  }

  public long getNumberOfCompletedOperations() {
    return completedOperations;
  }

  public void deactivate() {
//    System.out.println("Actor deactivated");
    active = false;
  }

  abstract protected int getNextPortionSize();
}
