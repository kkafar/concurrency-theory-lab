package main.experiment.log;

public final class LogOptions {
  public static int LOG_INSIDE_BUFFER = 1;
  public static int LOG_INSIDE_TASK = 1 << 1;
  public static int LOG_INSIDE_EXPERIMENT = 1 << 2;
  public static int LOG_EXPERIMENT = 1 << 3;

  private int mask = 0;

  LogOptions() {}

  void logInsideBuffer() {
    mask |= LOG_INSIDE_BUFFER;
  }

  void logInsideTask() {
    mask |= LOG_INSIDE_TASK;
  }

  void setLogInsideExperiment() {
    mask |= LOG_INSIDE_EXPERIMENT;
  }

  void logExperiment() {
    mask |= LOG_EXPERIMENT;
  }

  public boolean contains(int logFlag) {
    return (mask & logFlag) != 0;
  }
}
