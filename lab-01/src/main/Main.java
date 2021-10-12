package main;

public class Main {
  static int raceConditionedValue = 0;

  static final int nThreads = 10;
  static final int nIterations = 1000;

  public static void main(String[] args) throws InterruptedException {
    Thread[] threadPoolOne = new Thread[nThreads];
    Thread[] threadPoolTwo = new Thread[nThreads];

    for (int i = 0; i < nThreads; ++i) {
      threadPoolOne[i] = new Thread(() -> {
        for (int j = 0; j < nIterations; ++j) {
          raceConditionedValue++;
          raceConditionedValue--;
        }
      });
      threadPoolOne[i].start();

      threadPoolTwo[i] = new Thread(() -> {
        for (int j = 0; j < nIterations; ++j) {
          raceConditionedValue--;
          raceConditionedValue++;
        }
      });
      threadPoolTwo[i].start();
    }

    for (int i = 0; i < nThreads; ++i) {
      threadPoolOne[i].join();
      threadPoolTwo[i].join();
    }

    System.out.println("Value: " + raceConditionedValue);
  }
}
