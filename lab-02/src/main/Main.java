package main;

import main.concurrent.CustomSemaphore;
import main.tasks.prodcons.Consumer;
import main.tasks.prodcons.Producer;
import main.tasks.prodcons.SharedBuffer;

import java.util.Arrays;

public class Main {
  private static final int N_THREADS = 20;
  private static final int N_ITERATIONS = 1000;
  private static int sharedValue = 0;
  private static final int N_PRODUCERS = 5;
  private static final int N_CONSUMERS = 3;
  private static final int BUFF_BASE_SIZE = 20;

  public static void main(String[] args) throws InterruptedException {
    producerConsumer();
  }

  public static void producerConsumer() throws InterruptedException {
    SharedBuffer buffer = new SharedBuffer(BUFF_BASE_SIZE);

    Thread[] producers = new Thread[N_PRODUCERS];
    Thread[] consumers = new Thread[N_CONSUMERS];

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producers[i] = new Thread(new Producer(buffer, BUFF_BASE_SIZE));
    }

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumers[i] = new Thread(new Consumer(buffer, BUFF_BASE_SIZE, 100, 2000));
    }

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producers[i].start();
    }

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumers[i].start();
    }

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producers[i].join();
    }

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumers[i].join();
    }


  }

  public static void semapthoreTest() throws InterruptedException {
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
