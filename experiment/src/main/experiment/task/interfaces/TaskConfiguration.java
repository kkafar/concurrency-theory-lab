package main.experiment.task.interfaces;

abstract public class TaskConfiguration {
  protected final int numberOfProducers;
  protected final int numberOfConsumers;
  protected final int bufferSize;
  protected final long bufferOperationBound;
  protected final String description;
  protected final int extraTaskRepeats;

  public TaskConfiguration(
      final String description,
      final int numberOfProducers,
      final int numberOfConsumers,
      final int bufferSize,
      final long bufferOperationBound,
      final int extraTaskRepeats
  ) {
    this.description = description;
    this.numberOfConsumers = numberOfConsumers;
    this.numberOfProducers = numberOfProducers;
    this.bufferSize = bufferSize;
    this.bufferOperationBound = bufferOperationBound;
    this.extraTaskRepeats = extraTaskRepeats;
  }

  public int getNumberOfProducers() {
    return numberOfProducers;
  }

  public int getNumberOfConsumers() {
    return numberOfConsumers;
  }

  public int getBufferSize() {
    return bufferSize;
  }

  public long getBufferOperationBound() {
    return bufferOperationBound;
  }

  public int getExtraTaskRepeats() {
    return extraTaskRepeats;
  }

  public String getDescription() {
    return description + "\n" +
        "PRODUCERS: " +
        numberOfProducers + "\n" +
        "CONSUMERS: " +
        numberOfConsumers + "\n" +
        "BUFFER_SIZE: " +
        bufferSize + "\n" +
        "OPERATION_BOUND: " +
        bufferOperationBound + "\n" +
        "EX. TASKS: " +
        extraTaskRepeats + "\n";
  }
}
