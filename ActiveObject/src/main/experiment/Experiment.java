package main.experiment;

import main.actors.impls.*;
import main.actors.interfaces.Consumer;
import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.Producer;
import main.actors.interfaces.ProducerFactory;
import main.ao.client.interfaces.BufferProxy;
import main.ao.client.interfaces.BufferProxyFactory;
import main.ao.struct.impls.UnsyncPromise;
import main.buffer.interfaces.BufferFactory;
import main.experiment.result.ExperimentResult;
import main.experiment.task.Task;
import main.experiment.task.TaskConfiguration;
import main.experiment.task.TaskFactory;

import java.util.ArrayList;
import java.util.Collection;

public class Experiment {
  private final BufferProxyFactory bufferFactory;
  private final ProducerFactory producerFactory;
  private final ConsumerFactory consumerFactory;
  private final TaskFactory taskFactory;
  private final ExperimentResult experimentResult;
  private final long startingRngSeed;
  private final int singleTaskRepeats;

  private final ArrayList<Task> taskList;

  private BufferProxy buffer;

  public Experiment(
      final BufferProxyFactory bufferFactory,
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
            task.getBufferOperationBound(),
            startingRngSeed,
            singleTaskRepeats
        )
      );
    });
  }

  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    BufferProxy mockBuffer = bufferFactory.create(
        10,
        10,
        UnsyncPromise::new,
        false
    );

    Producer producer = producerFactory.create(mockBuffer, 10);
    if (producer instanceof RandomPortionProducer) {
      stringBuilder.append("Producer: RandomPortion\n");
    } else if (producer instanceof MinimalPortionProducer) {
      stringBuilder.append("Producer: MinimalPortion\n");
    } else if (producer instanceof MaximumPortionProducer) {
      stringBuilder.append("Producer: MaximumPortion\n");
    } else {
      stringBuilder.append("Producer: Unknown type\n");
    }

    Consumer consumer = consumerFactory.create(mockBuffer, 10);

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
    taskList.forEach(task -> {
      try {
        task.run();
        experimentResult.addTaskResult(task.getResult());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }
}
