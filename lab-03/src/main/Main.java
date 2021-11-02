package main;

//import main.hometask.fourconds.actors.Consumer;
//import main.hometask.fourconds.actors.Producer;
//import main.hometask.fourconds.buffer.SynchronizedCyclicBuffer;

import main.hometask.twoconds.actors.Consumer;
import main.hometask.twoconds.actors.Producer;
import main.hometask.twoconds.buffer.SynchronizedCyclicBuffer;

public class Main {
  private static final int N_PRODUCERS = 3;
  private static final int N_CONSUMERS = 3;
  private static final int BUFFER_SIZE = 10;

  public static void main(String[] args) throws InterruptedException {
    Producer[] producers = new Producer[N_PRODUCERS];
    Consumer[] consumers = new Consumer[N_CONSUMERS];

    SynchronizedCyclicBuffer buffer = new SynchronizedCyclicBuffer(BUFFER_SIZE, N_PRODUCERS + N_CONSUMERS, true);

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producers[i] = new Producer(buffer, "Producer no " + i, i);
    }

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumers[i] = new Consumer(buffer, "Consumer no " + i, N_PRODUCERS + i);
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

  public static void log(String message) {
    System.out.println(message);
  }
}
