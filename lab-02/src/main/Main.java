package main;

import main.concurrent.CustomSemaphore;

public class Main {
  private static final int N_THREADS = 20;
  private static final int N_ITERATIONS = 1000;
  private static int sharedValue = 0;

  public static void main(String[] args) throws InterruptedException {
    CustomSemaphore semaphore = new CustomSemaphore();

    Thread[] threadPool = new Thread[N_THREADS];
    Thread[] threadPool2 = new Thread[N_THREADS];

    for (int i = 0; i < N_THREADS; ++i) {
      threadPool[i] = new Thread(() -> {
        try {
          semaphore.acquire();
          for (int j = 0; j < N_ITERATIONS; ++j) {
            sharedValue++;
          }
          semaphore.release();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });

      threadPool2[i] = new Thread(() -> {
        try {
          semaphore.acquire();
          for (int j = 0; j < N_ITERATIONS; ++j) {
            sharedValue--;
          }
          semaphore.release();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    for (int i = 0; i < N_THREADS; ++i) {
      threadPool[i].start();
      threadPool2[i].start();
    }

    for (int i = 0; i < N_THREADS; ++i) {
      threadPool[i].join();
      threadPool2[i].join();
    }

    System.out.println("Value: " + sharedValue);
  }
}
