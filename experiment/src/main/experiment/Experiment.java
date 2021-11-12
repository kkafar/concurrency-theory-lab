package main.experiment;

import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;
import main.buffer.interfaces.Buffer;
import main.buffer.interfaces.BufferFactory;
import main.experiment.task.impl.StandardTask;

import java.util.ArrayList;
import java.util.Collection;

public class Experiment {
  private final BufferFactory bufferFactory;
  private final ProducerFactory producerFactory;
  private final ConsumerFactory consumerFactory;
  private final long startingRngSeed;

  private final ArrayList<StandardTask> taskList;

  private Buffer buffer;

  public Experiment(
      final BufferFactory bufferFactory,
      final ProducerFactory producerFactory,
      final ConsumerFactory consumerFactory,
      final long startingRngSeed
  ) {
    this.bufferFactory = bufferFactory;
    this.producerFactory = producerFactory;
    this.consumerFactory = consumerFactory;
    this.startingRngSeed = startingRngSeed;

    this.taskList = new ArrayList<>();
  }

  public void register(final StandardTask task) {
    this.taskList.add(task);
  }

  public void registerAll(final Collection<? extends StandardTask> taskCollection) {
    this.taskList.addAll(taskCollection);
  }
}
