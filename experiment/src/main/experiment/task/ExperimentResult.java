package main.experiment.task;

import main.experiment.task.interfaces.TaskResult;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ExperimentResult {
  private final LinkedList<TaskResult> results;

  public ExperimentResult() {
    results = new LinkedList<>();
  }

  public void addTaskResult(TaskResult result) {
    results.add(result);
  }

  public void addAllTaskResults(Collection<TaskResult> resultCollection) {
    results.addAll(resultCollection);
  }

  public List<TaskResult> getListOfResults() {
    return results;
  }
}
