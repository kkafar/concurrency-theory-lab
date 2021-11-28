package main.experiment.task;

import java.util.ArrayList;
import java.util.List;

abstract public class TaskResult {
  protected final List<Long> durationsInMs;
  protected final List<Long> operationsCompletedByProducers;
  protected final List<Long> operationsCompletedByConsumers;
  protected final int numberOfProducers;
  protected final int numberOfConsumers;
  protected final int numberOfRepeats;

  private String taskDescription = "NO TASK DESCRIPTION PROVIDED";

//  private static String taskSeparator = "===============================================\n";
//  private static String sectionSeparator = "+++++++++++++++++++++++++++++++++++++++++++++++\n";

  public TaskResult(final int numberOfProducers, final int numberOfConsumers, final int entries) {
    durationsInMs = new ArrayList<>(entries);
    operationsCompletedByConsumers = new ArrayList<>(entries);
    operationsCompletedByProducers = new ArrayList<>(entries);
    numberOfRepeats = entries;

    this.numberOfProducers = numberOfProducers;
    this.numberOfConsumers = numberOfConsumers;
  }

  public List<Long> getDurationsInMs() {
    return durationsInMs;
  }

  public List<Long> getOperationsCompletedByProducers() {
    return operationsCompletedByProducers;
  }

  public List<Long> getOperationsCompletedByConsumers() {
    return operationsCompletedByConsumers;
  }

  public String getDescription() {
    return taskDescription;
  }

  public void addDuration(long durationInMs) {
    durationsInMs.add(durationInMs);
  }

  public void addOperationsCompletedByProducer(long completedOperations) {
    operationsCompletedByProducers.add(completedOperations);
  }

  public void addOperationsCompletedByConsumer(long completedOperations) {
    operationsCompletedByConsumers.add(completedOperations);
  }

  public void setDescription(final String description) {
    taskDescription = description;
  }

  private String getDurationsDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Durations\n");
    for (long duration : durationsInMs) {
      stringBuilder.append(duration).append(" ");
    }
    return stringBuilder.append("\n").toString();
  }

  private String getOperationsByConsumersDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Consumer ops\nAll\n");
    for (long operations : operationsCompletedByConsumers) {
      stringBuilder.append(operations).append(" ");
    }


    return stringBuilder.append("\n").toString();
  }

  private String getOperationsByProducersDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Producer ops\nAll\n");
    for (long operations : operationsCompletedByProducers) {
      stringBuilder.append(operations).append(" ");
    }
    return stringBuilder.append("\n").toString();
  }

  public String toString() {
    return
        getDescription() +
        getDurationsDescription() +
        getOperationsByConsumersDescription() +
        getOperationsByProducersDescription();
  }
}
