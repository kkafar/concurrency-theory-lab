package main.experiment.task;

import java.util.Arrays;

public class TaskResult {
  private int durationsInMsIndex;
  private int producersIndex;
  private int consumersIndex;
  private final long[] durationsInMs;
  private final long[] tasksExecutedByProducers;
  private final long[] tasksExecutedByConsumers;

  public TaskResult(final int entries) {
    durationsInMs = new long[entries];
    tasksExecutedByProducers = new long[entries];
    tasksExecutedByConsumers = new long[entries];

    Arrays.fill(durationsInMs, 0);
    Arrays.fill(tasksExecutedByProducers, 0);
    Arrays.fill(tasksExecutedByConsumers, 0);
    durationsInMsIndex = 0;
  }

  public long[] getDurationsInMs() {
    return Arrays.copyOf(durationsInMs, durationsInMs.length);
  }

  public void addTaskDuration(long durationInMs) {
    durationsInMs[durationsInMsIndex++] = durationInMs;
  }
}
