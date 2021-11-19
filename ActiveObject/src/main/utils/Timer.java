package main.utils;

import java.time.Duration;

public class Timer {
  private long start;
  private long stop;
  private long elapsed;

  public Timer() {
  }

  public void start() {
    start = System.nanoTime();
  }

  public void stop() {
    stop = System.nanoTime();
  }

  public long getElapsed() {
    return Duration.ofNanos(stop - start).toMillis();
  }
}
