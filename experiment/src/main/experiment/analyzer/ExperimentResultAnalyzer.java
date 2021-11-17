package main.experiment.analyzer;

import main.experiment.ExperimentResult;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class ExperimentResultAnalyzer {
  public ExperimentResultAnalyzer() {
  }

  public void analyze(ExperimentResult result) {
    System.out.println(result.toString());
  }



  public void analyzeToFile(String path, ExperimentResult result) throws IOException {
    File logFile = new File(path);
    FileWriter fileWriter = new FileWriter(logFile, true);
    PrintWriter printWriter = new PrintWriter(fileWriter, true);

    printWriter.print(result.toString());
//    fileWriter.close();
    printWriter.close();
  }
}
