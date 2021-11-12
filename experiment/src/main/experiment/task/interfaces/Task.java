package main.experiment.task.interfaces;

public interface Task {
  void run() throws InterruptedException;
  TaskResult getResult();
}
