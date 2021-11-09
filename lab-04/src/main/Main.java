package main;

import main.lab.task1.buffer.SynchronizedCyclicBuffer;
import main.lab.task1.actors.Consumer;
import main.lab.task1.actors.Producer;
import main.lab.task1.buffer.SynchronizedCyclicBufferFourConds;
import main.lab.task1.buffer.SynchronizedCyclicBufferNestedLocksFactory;

public class Main {
  private static final int N_PRODUCERS = 3;
  private static final int N_CONSUMERS = 3;
  private static final int BUFFER_SIZE = 10;
  private static final int N_ITERATIONS = 20;
  private static final int N_ACTIONS = 100000;
  private static final int RNG_SEED = 10;

  public static void main(String[] args) throws InterruptedException {
    Experiment expNestedLocks = new Experiment(
        SynchronizedCyclicBuffer::new,
        BUFFER_SIZE,
        RNG_SEED
    );

    Experiment expFourLocks = new Experiment(
        SynchronizedCyclicBufferFourConds::new,
        BUFFER_SIZE,
        RNG_SEED
    );

    ExperimentResult expNestedLocksResult = expNestedLocks.conduct(
        N_ITERATIONS,
        N_ACTIONS,
        N_PRODUCERS,
        N_CONSUMERS
    );

    ExperimentResult expFourLocksResult = expFourLocks.conduct(
        N_ITERATIONS,
        N_ACTIONS,
        N_PRODUCERS,
        N_CONSUMERS
    );

    ExperimentResultsAnalyzer.analyze(expNestedLocksResult);

    ExperimentResultsAnalyzer.analyze(expFourLocksResult);
  }
}
