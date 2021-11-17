package main.experiment.log;

public class LogOptionsBuilder {
  private final LogOptions logOptions;

  public LogOptionsBuilder() {
    logOptions = new LogOptions();
  }

  public LogOptionsBuilder logInsideBuffer() {
    logOptions.logInsideBuffer();
    return this;
  }

  public LogOptionsBuilder logInsideTask() {
    logOptions.logInsideTask();
    return this;
  }

  public LogOptionsBuilder setLogInsideExperiment() {
    logOptions.setLogInsideExperiment();
    return this;
  }

  public LogOptionsBuilder logExperiment() {
    logOptions.logExperiment();
    return this;
  }

  public LogOptions build() {
    return logOptions;
  }
}
