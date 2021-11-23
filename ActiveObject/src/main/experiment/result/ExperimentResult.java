package main.experiment.result;

import main.experiment.task.TaskResult;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ExperimentResult {
  private final LinkedList<TaskResult> results;
  private String description;

  public ExperimentResult() {
    description = "DESCRIPTION NOT PROVIDED";
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

  public void addExperimentDescription(String description) {
    this.description = description;
  }

  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(description).append("\n");
    for (TaskResult result : results) {
      stringBuilder.append(result.toString());
    }
    return stringBuilder.toString();
  }
}
