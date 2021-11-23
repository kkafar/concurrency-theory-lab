package main.experiment.task;

public final class StandardTaskConfiguration extends TaskConfiguration {
  public StandardTaskConfiguration(
      String description,
      int numberOfProducers,
      int numberOfConsumers,
      int bufferSize,
      long bufferOperationBound,
      int extraTaskRepeats
  ) {
    super(description, numberOfProducers, numberOfConsumers, bufferSize, bufferOperationBound, extraTaskRepeats);
  }
}
