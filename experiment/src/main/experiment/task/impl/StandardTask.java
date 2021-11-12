package main.experiment.task.impl;

import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;
import main.buffer.interfaces.BufferFactory;
import main.actors.interfaces.Consumer;
import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;
import main.experiment.task.interfaces.Task;
import main.experiment.task.interfaces.TaskResult;
import main.utils.Timer;

public class StandardTask implements Task {
  private long actions = 0;
  private int bufferSize = 0;
  private int numberOfProducers = 0;
  private int numberOfConsumers = 0;
  private int repeats = 0;
  private boolean bufferLog = false;

  private Producer[] producers;
  private Consumer[] consumers;

  private Buffer buffer;
  private StandardTaskResult taskResult;

  private final long initialRngSeed;
  private final String description;

  private final Timer timer;
  private final BufferFactory bufferFactory;
  private final ProducerFactory producerFactory;
  private final ConsumerFactory consumerFactory;

  public StandardTask(
      final String description,
      final int numberOfProducers,
      final int numberOfConsumers,
      final ProducerFactory producerFactory,
      final ConsumerFactory consumerFactory,
      final BufferFactory bufferFactory,
      final long startingRngSeed,
      final int repeats
  ) {
    this.initialRngSeed = startingRngSeed;
    this.timer = new Timer();
    this.bufferFactory = bufferFactory;
    this.producerFactory = producerFactory;
    this.consumerFactory = consumerFactory;
    this.description = description;
    this.numberOfConsumers = numberOfConsumers;
    this.numberOfProducers = numberOfProducers;
    this.repeats = repeats;
  }

  public StandardTask setBufferLog(final boolean flag) {
    bufferLog = flag;
  }

  private void setup() {
    producers = new Producer[numberOfProducers];
    consumers = new Consumer[numberOfConsumers];
    buffer = bufferFactory.create(bufferSize, actions, bufferLog);
    initProducers();
    initConsumers();
  }

  private void initProducers() {
    for (int i = 0; i < numberOfProducers; ++i) {
      producers[i] = producerFactory.create(buffer, initialRngSeed);
    }
  }

  private void initConsumers() {
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i] = consumerFactory.create(buffer, initialRngSeed);
    }
  }

  private void start() {
    for (int i = 0; i < numberOfProducers; ++i) {
      producers[i].start();
    }
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i].start();
    }
  }

  @Override
  public TaskResult getResult() {
    return null;
  }

  private void join() throws InterruptedException {
    for (int i = 0; i < numberOfProducers; ++i) {
      producers[i].join();
    }
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i].join();
    }
  }

  public StandardTaskResult getTaskResult() {
    return taskResult;
  }

  public void run() throws InterruptedException {
    taskResult = new StandardTaskResult(repeats);
    for (int i = 0; i < repeats; ++i) {
      setup();
      timer.start();
      start();
      join();
      timer.stop();
      taskResult.addTaskDuration(timer.getElapsed());
    }
  }
}
