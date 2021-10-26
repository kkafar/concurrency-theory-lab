package main;

import main.labtask2.actors.Consumer;
import main.labtask2.actors.Producer;
import main.labtask2.buffer.SynchronizedCyclicBuffer;

public class Main {
  private static final int N_PRODUCERS = 2;
  private static final int N_CONSUMERS = 3;
  private static final int BUFFER_SIZE = 10;

  public static void main(String[] args) throws InterruptedException {
    Producer[] producers = new Producer[N_PRODUCERS];
    Consumer[] consumers = new Consumer[N_CONSUMERS];

    Thread[] producerThreads = new Thread[N_PRODUCERS];
    Thread[] consumerThreads = new Thread[N_CONSUMERS];

    SynchronizedCyclicBuffer buffer = new SynchronizedCyclicBuffer(BUFFER_SIZE, true);

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producers[i] = new Producer(buffer);
      producerThreads[i] = new Thread(producers[i]);
    }

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumers[i] = new Consumer(buffer);
      consumerThreads[i] = new Thread(consumers[i]);
    }

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producerThreads[i].start();
    }

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumerThreads[i].start();
    }

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producerThreads[i].join();
    }

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumerThreads[i].join();
    }
  }

  public static void log(String message) {
    System.out.println(message);
  }
}
