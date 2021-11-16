package main.experiment.analyzer;

import main.experiment.ExperimentResult;
import main.experiment.task.interfaces.TaskResult;

public class ExperimentResultAnalyzer {
  public ExperimentResultAnalyzer() {}

  public void analyze(ExperimentResult result) {
    System.out.println(result.toString());
  }

  private static String printDurations(TaskResult taskResult) {
    StringBuilder stringBuilder = new StringBuilder();

    for (long duration : taskResult.getDurationsInMs()) {
      stringBuilder.append(duration + " ");
    }
    return stringBuilder.toString();
  }
}
