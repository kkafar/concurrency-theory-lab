package main.experiment.task;

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
}
