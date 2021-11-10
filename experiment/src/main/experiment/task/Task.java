package main.experiment.task;

import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;
import main.buffer.interfaces.BufferFactory;
import main.actors.interfaces.Consumer;
import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;
import main.utils.Timer;

public class Task {
  private long actions = 0;
  private int bufferSize = 0;
  private int numberOfProducers = 0;
  private int numberOfConsumers = 0;
  private int repeats = 0;
  private boolean bufferLog = false;
  private boolean isConfigured = false;

  private Producer[] producers;
  private Consumer[] consumers;

  private Buffer buffer;
  private TaskResult taskResult;

  private final long rngSeed;
  private final String description;

  private final Timer timer;
  private final BufferFactory bufferFactory;
  private final ProducerFactory producerFactory;
  private final ConsumerFactory consumerFactory;

  public Task(
      final String description,
      final ProducerFactory producerFactory,
      final ConsumerFactory consumerFactory,
      final BufferFactory bufferFactory,
      final long rngSeed
  ) {
    this.rngSeed = rngSeed;
    this.timer = new Timer();
    this.bufferFactory = bufferFactory;
    this.producerFactory = producerFactory;
    this.consumerFactory = consumerFactory;
    this.description = description;
  }

  public Task configure(
      final int numberOfProducers,
      final int numberOfConsumers,
      final int repeats,
      final int bufferSize
  ) {
    this.numberOfProducers = numberOfProducers;
    this.numberOfConsumers = numberOfConsumers;
    this.repeats = repeats;
    this.bufferSize = bufferSize;

    setup();

    this.isConfigured = true;
    return this;
  }

  public Task setBufferLog(final boolean flag) {
    bufferLog = flag;
    return this;
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
      producers[i] = producerFactory.create(buffer, rngSeed);
    }
  }

  private void initConsumers() {
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i] = consumerFactory.create(buffer, rngSeed);
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

  private void join() throws InterruptedException {
    for (int i = 0; i < numberOfProducers; ++i) {
      producers[i].join();
    }
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i].join();
    }
  }

  public TaskResult getTaskResult() {
    return taskResult;
  }

  public TaskResult conduct(
  ) throws InterruptedException {
    if (!isConfigured) {
      throw new IllegalStateException("Task must be configured before being started.");
    }
    taskResult = new TaskResult(repeats);
    for (int i = 0; i < repeats; ++i) {
      setup();
      timer.start();
      start();
      join();
      timer.stop();
      taskResult.addTaskDuration(timer.getElapsed());
    }
    return taskResult;
  }
}
