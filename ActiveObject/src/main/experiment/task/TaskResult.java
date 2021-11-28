package main.experiment.task;

import java.util.*;

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
    stringBuilder.append("\nMean ").append(getMean(durationsInMs)).append("\n");
    return stringBuilder.toString();
  }

  private String getOperationsByConsumersDescription() {
    return getActorOperationsDescription("Consumer", operationsCompletedByConsumers);
  }

  private String getOperationsByProducersDescription() {
    return getActorOperationsDescription("Producer", operationsCompletedByProducers);
  }

  private String getActorOperationsDescription(String actor, List<Long> completedOperations) {
    if (!actor.equals("Consumer") && !actor.equals("Producer")) {
      throw new IllegalArgumentException("Actor must be one of: \"Consumer\", \"Producer\"");
    }

    StringBuilder opsDescription = new StringBuilder();
    opsDescription.append(actor).append(" ops\nAll\n");

    completedOperations.forEach((ops) -> {
      opsDescription.append(ops).append(" ");
    });

    opsDescription.append("\nMean ").append(getMean(completedOperations)).append("\n");

    return opsDescription.toString();
  }

  private long getSumOf(Collection<Long> collection) {
    long sum = 0;
    for (long elem : collection) {
      sum += elem;
    }
    return sum;
  }

  private double getMean(Collection<Long> collection) {
    return ((double) (getSumOf(collection))) / collection.size();
  }


  public String toString() {
    return
        getDescription() +
        getDurationsDescription() +
        getOperationsByConsumersDescription() +
        getOperationsByProducersDescription();
  }
}
