package main;

import main.lab.task1.buffer.SynchronizedCyclicBuffer;
import main.lab.task1.actors.Consumer;
import main.lab.task1.actors.Producer;

public class Main {
  private static final int N_PRODUCERS = 3;
  private static final int N_CONSUMERS = 3;
  private static final int BUFFER_SIZE = 10;

  public static void main(String[] args) throws InterruptedException {
    Producer[] producers = new Producer[N_PRODUCERS];
    Consumer[] consumers = new Consumer[N_CONSUMERS];

    SynchronizedCyclicBuffer buffer = new SynchronizedCyclicBuffer(BUFFER_SIZE, true);

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producers[i] = new Producer(buffer);
    }

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumers[i] = new Consumer(buffer);
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
}
