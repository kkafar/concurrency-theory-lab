package main;

import java.util.Arrays;

public class ExperimentResult {
  private int index;
  private final long[] durationsInMs;

  public ExperimentResult(final int timeEntries) {
    durationsInMs = new long[timeEntries];
    Arrays.fill(durationsInMs, 0);
    index = 0;
  }

  public long[] getDurationsInMs() {
    return Arrays.copyOf(durationsInMs, durationsInMs.length);
  }

  public void add(long durationInMs) {
    durationsInMs[index++] = durationInMs;
  }
}
