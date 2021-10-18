package main;

import main.custom.ClassWithExpressiveConstructor;
import main.custom.Monitor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
  private static final int N_ITER = 10;
  private static final int N_THREADS = 5;

  public static void main(String[] args) throws InterruptedException {
    Monitor monitorInstance = new Monitor(0);
    Thread[] threadPoolIncrement = new Thread[N_THREADS / 2];
    Thread[] threadPoolDecrement = new Thread[N_THREADS / 2];

    for (int j = 0; j < N_THREADS / 2; ++j) {
      threadPoolIncrement[j] = new Thread(() -> {
        for (int i = 0; i < N_ITER; ++i) {
          monitorInstance.modifyValueBy(1);
        }
      });
      threadPoolDecrement[j] = new Thread(() -> {
        for (int i = 0; i < N_ITER; ++i) {
          monitorInstance.modifyValueBy(-1);
        }
      });
    }

    for (int j = 0; j < N_THREADS / 2; ++j) {
      threadPoolIncrement[j].start();
      threadPoolDecrement[j].start();
    }

    for (int j = 0; j < N_THREADS / 2; ++j) {
      threadPoolIncrement[j].join();
      threadPoolDecrement[j].join();
    }

    System.out.println("Value: " + monitorInstance.getSharedValue());
  }
}
