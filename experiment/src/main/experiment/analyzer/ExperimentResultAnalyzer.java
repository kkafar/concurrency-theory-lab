package main.experiment.analyzer;

import main.experiment.ExperimentResult;
import main.experiment.task.interfaces.TaskResult;

public class ExperimentResultAnalyzer {
  public ExperimentResultAnalyzer() {}

  private static String taskSeparator = "===============================================";
  private static String sectionSeparator = "+++++++++++++++++++++++++++++++++++++++++++++++";

  public void analyze(ExperimentResult result) {
    for (TaskResult taskResult : result.getListOfResults()) {
    }
  }

  private static String printDurations(TaskResult taskResult) {
    StringBuilder stringBuilder = new StringBuilder();

    for (long duration : taskResult.getDurationsInMs()) {
      stringBuilder.append(duration + " ");
    }
  }



}
