package main;

import main.lab.Consumer;
import main.lab.Producer;
import main.lab.SynchronizedBuffer;

public class Main {
  static int raceConditionedValue = 0;

  static final int nThreads = 10;
  static final int nIterations = 1000;

  private static final int nProducers = 1;
  private static final int nConsumers = 2;

  public static void main(String[] args) throws InterruptedException {
    lab2();
  }

  public static void lab2() throws InterruptedException {
    SynchronizedBuffer buffer = new SynchronizedBuffer();
    Thread[] producers = new Thread[nProducers];
    Thread[] consumers = new Thread[nConsumers];

    for (int i = 0; i < nProducers; ++i) {
      producers[i] = new Thread(new Producer(buffer));
    }

    for (int i = 0; i < nConsumers; ++i) {
      consumers[i] = new Thread(new Consumer(buffer));
    }

    for (int i = 0; i < nProducers; ++i) {
      producers[i].start();
    }

    for (int i = 0; i < nConsumers; ++i) {
      consumers[i].start();
    }

    for (int i = 0; i < nProducers; ++i) {
      producers[i].join();
    }

    for (int i = 0; i < nConsumers; ++i) {
      consumers[i].join();
    }

  }

  public void lab1() throws InterruptedException {
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
