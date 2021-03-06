package main.experiment.task.impl;

import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;
import main.buffer.interfaces.BufferFactory;
import main.actors.interfaces.Consumer;
import main.actors.interfaces.Producer;
import main.buffer.interfaces.Buffer;
import main.experiment.log.LogOptions;
import main.experiment.task.interfaces.Task;
import main.experiment.task.interfaces.TaskResult;
import main.utils.Timer;

public final class StandardTask implements Task {
  private final long bufferOperationsBound;
  private final int bufferSize;
  private final int numberOfProducers;
  private final int numberOfConsumers;
  private final int repeats;

  private Producer[] producers;
  private Consumer[] consumers;

  private Buffer buffer;
  private StandardTaskResult taskResult;
  private LogOptions logOptions = null;

  private final long initialRngSeed;
  private final String description;

  private final Timer timer;
  private final BufferFactory bufferFactory;
  private final ProducerFactory producerFactory;
  private final ConsumerFactory consumerFactory;

  private final int extraTaskRepeats;

  public StandardTask(
      final String description,
      final int numberOfProducers,
      final int numberOfConsumers,
      final ProducerFactory producerFactory,
      final ConsumerFactory consumerFactory,
      final BufferFactory bufferFactory,
      final int bufferSize,
      final int extraTaskRepeats,
      final long bufferOperationsBound,
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
    this.bufferSize = bufferSize;
    this.extraTaskRepeats = extraTaskRepeats;
    this.bufferOperationsBound = bufferOperationsBound;
    this.taskResult = new StandardTaskResult(repeats);

    // setup is called in start() method
//    setup();
  }

  private void setup() {
    producers = new Producer[numberOfProducers];
    consumers = new Consumer[numberOfConsumers];
    buffer = bufferFactory.create(bufferSize, bufferOperationsBound, false);
    initProducers();
    initConsumers();
    buffer.setLog(logOptions != null && logOptions.contains(LogOptions.LOG_INSIDE_BUFFER));
  }

  private void initProducers() {
    for (int i = 0; i < numberOfProducers; ++i) {
      producers[i] = producerFactory.create(buffer, extraTaskRepeats, initialRngSeed);
      producers[i].setName("PRODUCER " + i);
    }
  }

  private void initConsumers() {
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i] = consumerFactory.create(buffer, extraTaskRepeats, initialRngSeed);
      consumers[i].setName("CONSUMER " + i);
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
    return taskResult;
  }

  @Override
  public void setLogOptions(LogOptions logOptions) {
    this.logOptions = logOptions;
  }

  private void join() throws InterruptedException {
    for (int i = 0; i < numberOfProducers; ++i) {
      producers[i].join();
    }
    for (int i = 0; i < numberOfConsumers; ++i) {
      consumers[i].join();
    }
  }

  private void extractResult() {
    taskResult.addTaskDuration(timer.getElapsed());

    for (Producer producer : producers) {
      taskResult.addOperationsCompletedByProducer(
          producer.getNumberOfCompletedOperations()
      );
    }

    for (Consumer consumer : consumers) {
      taskResult.addOperationsCompletedByConsumer(
          consumer.getNumberOfCompletedOperations()
      );
    }
  }

  public StandardTaskResult getTaskResult() {
    return taskResult;
  }

  public String getDescription() {
    return description;
  }

  public String toString() {
    return description;
  }

  public void run() throws InterruptedException {
    if (logOptions != null && logOptions.contains(LogOptions.LOG_INSIDE_TASK)) {
      System.out.println(this);
    }
    for (int i = 0; i < repeats; ++i) {
      setup();
      timer.start();
      start();
      join();
      timer.stop();
      extractResult();
    }
    taskResult.setTaskDescription(getDescription());
  }
}
