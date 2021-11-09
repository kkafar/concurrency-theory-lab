package main;

import java.util.Arrays;
import java.util.OptionalDouble;

public class ExperimentResultsAnalyzer {
  public static void analyze(ExperimentResult experimentResult) {
    long[] durations = experimentResult.getDurationsInMs();
    System.out.println("----------------------------------------------");
    System.out.println("Durations of particular tests [ms]");
    System.out.println(Arrays.toString(durations));
    System.out.println("----------------------------------------------");
    System.out.println(Arrays.stream(durations).summaryStatistics());
    System.out.println("----------------------------------------------");
    Arrays.sort(durations);
  }
}
