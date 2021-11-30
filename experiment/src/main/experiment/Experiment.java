package main.experiment;

import main.actors.impl.*;
import main.actors.interfaces.Consumer;
import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.Producer;
import main.actors.interfaces.ProducerFactory;
import main.buffer.impl.FourCondsBufferProxy;
import main.buffer.impl.ThreeLocksBufferProxy;
import main.buffer.interfaces.Buffer;
import main.buffer.interfaces.BufferFactory;
import main.experiment.log.LogOptions;
import main.experiment.task.interfaces.Task;
import main.experiment.task.interfaces.TaskConfiguration;
import main.experiment.task.interfaces.TaskFactory;

import java.util.ArrayList;
import java.util.Collection;

public class Experiment {
  private final BufferFactory bufferFactory;
  private final ProducerFactory producerFactory;
  private final ConsumerFactory consumerFactory;
  private final TaskFactory taskFactory;
  private final ExperimentResult experimentResult;
  private final long startingRngSeed;
  private final int singleTaskRepeats;

  private final ArrayList<Task> taskList;

  private Buffer buffer;
  private LogOptions logOptions = null;

  public Experiment(
      final BufferFactory bufferFactory,
      final ProducerFactory producerFactory,
      final ConsumerFactory consumerFactory,
      final TaskFactory taskFactory,
      final long startingRngSeed,
      final int singleTaskRepeats
  ) {
    this.bufferFactory = bufferFactory;
    this.producerFactory = producerFactory;
    this.consumerFactory = consumerFactory;
    this.taskFactory = taskFactory;
    this.startingRngSeed = startingRngSeed;
    this.singleTaskRepeats = singleTaskRepeats;
    this.experimentResult = new ExperimentResult();
    this.taskList = new ArrayList<>();
  }

  public void register(final TaskConfiguration task) {
    taskList.add(taskFactory.create(
        task.getDescription(),
        task.getNumberOfProducers(),
        task.getNumberOfConsumers(),
        producerFactory,
        consumerFactory,
        bufferFactory,
        task.getBufferSize(),
        task.getExtraTaskRepeats(),
        task.getBufferOperationBound(),
        startingRngSeed,
        singleTaskRepeats
    ));
  }

  public void registerAll(final Collection<TaskConfiguration> tasks) {
    tasks.forEach(task -> {
      taskList.add(
        taskFactory.create(
            task.getDescription(),
            task.getNumberOfProducers(),
            task.getNumberOfConsumers(),
            producerFactory,
            consumerFactory,
            bufferFactory,
            task.getBufferSize(),
            task.getExtraTaskRepeats(),
            task.getBufferOperationBound(),
            startingRngSeed,
            singleTaskRepeats
        )
      );
    });
  }

  public void setLogOptions(LogOptions logOptions) {
    this.logOptions = logOptions;
  }

  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    Buffer mockBuffer = bufferFactory.create(10, 10, false);

//    if (mockBuffer instanceof FourCondsBufferProxy) {
//      stringBuilder.append("Buffer: FourCondsBufferProxy\n");
//    } else if (mockBuffer instanceof ThreeLocksBufferProxy) {
//      stringBuilder.append("Buffer: ThreeLocksBufferProxy\n");
//    } else {
//      stringBuilder.append("Buffer: Unknown type\n");
//    }

    Producer producer = producerFactory.create(mockBuffer, 10, 10);
    if (producer instanceof RandomPortionProducer) {
      stringBuilder.append("Producer: RandomPortion\n");
    } else if (producer instanceof MinimalPortionProducer) {
      stringBuilder.append("Producer: MinimalPortion\n");
    } else if (producer instanceof MaximumPortionProducer) {
      stringBuilder.append("Producer: MaximumPortion\n");
    } else {
      stringBuilder.append("Producer: Unknown type\n");
    }

    Consumer consumer = consumerFactory.create(mockBuffer, 10, 10);

    if (consumer instanceof RandomPortionConsumer) {
      stringBuilder.append("Consumer: RandomPortion\n");
    } else if (consumer instanceof MinimalPortionConsumer) {
      stringBuilder.append("Consumer: MinimalPortion\n");
    } else if (consumer instanceof MaximumPortionConsumer) {
      stringBuilder.append("Consumer: MaximumPortion\n");
    } else {
      stringBuilder.append("Consumer: Unknown type\n");
    }

    return stringBuilder
        .append("Initial seed: ")
        .append(startingRngSeed)
        .append("\nRepeats: ")
        .append(singleTaskRepeats)
        .append("\n")
        .toString();
  }

  /**
   * Returns result of conducted experiment. Note that conduct() method should be called
   * before obtaining the result.
   *
   * @return experiment result
   */
  public ExperimentResult getResult() {
    return experimentResult;
  }

  /**
   * Executes all registered tasks and saves their results. Result is later
   * available via getResult method
   */
  public void conduct() {
    experimentResult.addExperimentDescription(toString());
    if (logOptions != null && logOptions.contains(LogOptions.LOG_EXPERIMENT)) {
      System.out.println(this);
    }
    taskList.forEach(task -> {
      task.setLogOptions(logOptions);
      try {
        task.run();
        experimentResult.addTaskResult(task.getResult());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }
}
