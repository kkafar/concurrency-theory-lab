package main.experiment;

import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;
import main.buffer.interfaces.Buffer;
import main.buffer.interfaces.BufferFactory;
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
   * available via getResult() method
   */
  public void conduct() {
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
