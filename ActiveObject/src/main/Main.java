package main;

import main.actors.impls.RandomPortionConsumer;
import main.actors.impls.RandomPortionProducer;
import main.actors.interfaces.Consumer;
import main.actors.interfaces.Producer;
import main.ao.client.impls.SharedBufferProxy;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;

public class Main {
  private static final int N_CONSUMERS = 2;
  private static final int N_PRODUCERS = 2;
  private static final long RNG_SEED = 10;
  private static final int BUFFER_SIZE = 10;
  private static final long MAX_OPS = 5;

  public static void main(String[] args) throws InterruptedException {
    testCase();
  }

  private static void testCase() throws InterruptedException {
    SharedBufferProxy bufferProxy = new SharedBufferProxy(
        BUFFER_SIZE,
        MAX_OPS
    );
    Consumer[] consumers = new Consumer[N_CONSUMERS];
    Producer[] producers = new Producer[N_PRODUCERS];

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumers[i] = new RandomPortionConsumer(
          bufferProxy,
          RNG_SEED
      );
    }

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producers[i] = new RandomPortionProducer(
          bufferProxy,
          RNG_SEED
      );
    }

    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumers[i].start();
    }

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producers[i].start();
    }


    for (int i = 0; i < N_CONSUMERS; ++i) {
      consumers[i].join();
    }

    for (int i = 0; i < N_PRODUCERS; ++i) {
      producers[i].join();
    }

    bufferProxy.getScheduler().join();

  }
}
