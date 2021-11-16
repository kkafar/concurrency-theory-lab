package main.experiment.task.interfaces;

import main.experiment.log.LogOptions;

public interface Task {
  /**
   * Execute task
   *
   * @throws InterruptedException
   */
  void run() throws InterruptedException;

  /**
   * Returns result of executed task including time measurements, operation executed
   * by particular actors, etc.
   *
   * @return result of executed task
   */
  TaskResult getResult();

  /**
   * @param logOptions logging options
   */
  void setLogOptions(final LogOptions logOptions);
}
