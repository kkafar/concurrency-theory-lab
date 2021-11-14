package main.experiment.task.impl;

import main.experiment.task.interfaces.TaskConfiguration;

public class StandardTaskConfiguration extends TaskConfiguration {
  public StandardTaskConfiguration(
      String description,
      int numberOfProducers,
      int numberOfConsumers,
      int bufferSize,
      long bufferOperationBound
  ) {
    super(description, numberOfProducers, numberOfConsumers, bufferSize, bufferOperationBound);
  }
}