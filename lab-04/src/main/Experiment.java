package main;

import main.lab.task1.actors.Consumer;
import main.lab.task1.actors.Producer;
import main.lab.task1.buffer.Buffer;
import main.lab.task1.buffer.BufferFactory;
import main.lab.task1.buffer.SynchronizedCyclicBuffer;
import main.utils.Timer;

public class Experiment {
  private final int numberOfConsumers;
  private final int numberofProducers;
  private final int bufferSize;
  private final long iterations;
  private final long rngSeed;

  private final Producer[] producers;
  private final Consumer[] consumers;

  private final Buffer buffer;

  private final Timer timer;

  public Experiment(
      final BufferFactory bufferFactory,
      final int numberOfConsumers,
      final int numberOfProducers,
      final int bufferSize,
      final long iterations,
      final long rngSeed
  ) {
    this.producers = new Producer[numberOfProducers];
    this.consumers = new Consumer[numberOfConsumers];
    this.numberOfConsumers = numberOfConsumers;
    this.numberofProducers = numberOfProducers;
    this.bufferSize = bufferSize;
    this.iterations = iterations;
    this.rngSeed = rngSeed;
    this.buffer = bufferFactory.create(bufferSize, true);
    this.timer = new Timer();

    initConsumers();
    initProducers();
  }

  private void initProducers() {
    for (int i = 0; i < numberofProducers; ++i) {
      producers[i] = new Producer(buffer, iterations, rngSeed);
    }
  }

  private void initConsumers() {
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i] = new Consumer(buffer, iterations, rngSeed);
    }
  }

  private void start() {
    for (int i = 0; i < numberofProducers; ++i) {
      producers[i].start();
    }
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i].start();
    }
  }

  private void join() throws InterruptedException {
    for (int i = 0; i < numberofProducers; ++i) {
      producers[i].join();
    }
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i].join();
    }
  }

  public ExperimentResult conduct() throws InterruptedException {
    start();
    join();
    return null;
  }

}
