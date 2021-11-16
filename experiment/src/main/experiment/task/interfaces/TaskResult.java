package main.experiment.task.interfaces;

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

  public String getTimeLine() {
     
  }

  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(taskSeparator);
    stringBuilder.append(getTaskDescription());
    stringBuilder.append(sectionSeparator);



    stringBuilder.append(taskSeparator);
    return stringBuilder.toString();
  }
}
