package main;

import main.lab.task1.buffer.SynchronizedCyclicBuffer;
import main.lab.task1.actors.Consumer;
import main.lab.task1.actors.Producer;
import main.lab.task1.buffer.SynchronizedCyclicBufferNestedLocksFactory;

public class Main {
  private static final int N_PRODUCERS = 3;
  private static final int N_CONSUMERS = 3;
  private static final int BUFFER_SIZE = 10;
  private static final long N_ITERATIONS = 100;
  private static final long RNG_SEED = 10;

  public static void main(String[] args) throws InterruptedException {
    Experiment expNestedLocks = new Experiment(
        SynchronizedCyclicBuffer::new,
        N_CONSUMERS,
        N_PRODUCERS,
        BUFFER_SIZE,
        N_ITERATIONS,
        RNG_SEED
    );

    expNestedLocks.conduct();
  }
}
