package main;

import main.lab.task1.actors.Consumer;
import main.lab.task1.actors.Producer;
import main.lab.task1.buffer.Buffer;
import main.lab.task1.buffer.BufferFactory;
import main.utils.Timer;

public class Experiment {
//  private int numberOfConsumers;
//  private int numberOfProducers;
  private final int bufferSize;
  private long actions;

  private final long rngSeed;

  private Producer[] producers;
  private Consumer[] consumers;

  private Buffer buffer;

  private final Timer timer;
  private final BufferFactory bufferFactory;

  public Experiment(
      final BufferFactory bufferFactory,
      final int bufferSize,
      final long rngSeed
  ) {
    this.rngSeed = rngSeed;
    this.timer = new Timer();
    this.bufferFactory = bufferFactory;
    this.bufferSize = bufferSize;
  }

  private void setup(
      final int numberOfProducers,
      final int numberOfConsumers
  ) {
    producers = new Producer[numberOfProducers];
    consumers = new Consumer[numberOfConsumers];
    buffer = bufferFactory.create(bufferSize, actions,false);
    initProducers(numberOfProducers);
    initConsumers(numberOfConsumers);
  }

  private void initProducers(final int numberOfProducers) {
    for (int i = 0; i < numberOfProducers; ++i) {
      producers[i] = new Producer(buffer, actions, rngSeed);
    }
  }

  private void initConsumers(final int numberOfConsumers) {
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i] = new Consumer(buffer, actions, rngSeed);
    }
  }

  private void start(final int numberOfProducers, final int numberOfConsumers) {
    for (int i = 0; i < numberOfProducers; ++i) {
      producers[i].start();
    }
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i].start();
    }
  }

  private void join(final int numberOfProducers, final int numberOfConsumers) throws InterruptedException {
    for (int i = 0; i < numberOfProducers; ++i) {
      producers[i].join();
    }
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i].join();
    }
  }

  public ExperimentResult conduct(
      final int iterations,
      final int actions,
      final int numberOfProducers,
      final int numberOfConsumers
  ) throws InterruptedException {
    ExperimentResult experimentResult = new ExperimentResult(iterations);
    this.actions = actions;
    for (int i = 0; i < iterations; ++i) {
      setup(numberOfProducers, numberOfConsumers);
      timer.start();
      start(numberOfProducers, numberOfConsumers);
      join(numberOfProducers, numberOfConsumers);
      timer.stop();
      experimentResult.add(timer.getElapsed());
    }
    return experimentResult;
  }
}
