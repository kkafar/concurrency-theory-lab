package main.experiment.task;

import java.util.ArrayList;
import java.util.List;

abstract public class TaskResult {
  protected final List<Long> durationsInMs;
  protected final List<Long> operationsCompletedByProducers;
  protected final List<Long> operationsCompletedByConsumers;

  private String taskDescription = "NO TASK DESCRIPTION PROVIDED";

  private static String taskSeparator = "===============================================\n";
  private static String sectionSeparator = "+++++++++++++++++++++++++++++++++++++++++++++++\n";

  public TaskResult(final int entries) {
    durationsInMs = new ArrayList<>(entries);
    operationsCompletedByConsumers = new ArrayList<>(entries);
    operationsCompletedByProducers = new ArrayList<>(entries);
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

  public String getTaskDescription() {
    return taskDescription;
  }

  public void addTaskDuration(long durationInMs) {
    durationsInMs.add(durationInMs);
  }

  public void addOperationsCompletedByProducer(long completedOperations) {
    operationsCompletedByProducers.add(completedOperations);
  }

  public void addOperationsCompletedByConsumer(long completedOperations) {
    operationsCompletedByConsumers.add(completedOperations);
  }

  public void setTaskDescription(final String description) {
    taskDescription = description;
  }

  private String getDurationsDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Durations\n[ ");
    for (long duration : durationsInMs) {
      stringBuilder.append(duration).append(" ");
    }
    return stringBuilder.append("]\n").toString();
  }

  private String getOperationsByConsumersDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Consumers\n[ ");
    for (long operations : operationsCompletedByConsumers) {
      stringBuilder.append(operations).append(" ");
    }
    return stringBuilder.append("]\n").toString();
  }

  private String getOperationsByProducersDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Producers\n[ ");
    for (long operations : operationsCompletedByProducers) {
      stringBuilder.append(operations).append(" ");
    }
    return stringBuilder.append("]\n").toString();
  }

  public String toString() {
    return taskSeparator +
        getTaskDescription() +
        getDurationsDescription() +
        getOperationsByConsumersDescription() +
        getOperationsByProducersDescription() +
        taskSeparator +
        "\n";
  }
}
