package main;

import main.actors.Consumer;
import main.actors.Producer;
import main.actors.SinglePortionGenerator;
import main.buffer.Controller;
import org.jcsp.lang.*;

import java.util.List;

public class Main {
  private static final int nProducers = 1;
  private static final int nConsumers = 1;
  private static final int nBuffers = 2;
  private static final List<Integer> bufferCapacities = List.of(10, 10);

  public static void main(String[] args) {
    final Producer[] producers = new Producer[nProducers];
    final Consumer[] consumers = new Consumer[nProducers];
    final Controller controller = new Controller();

    for (int i = 0; i < nProducers; ++i) {
//      producers[i] = new Producer();
    }


    final One2OneChannel communicationChannel = Channel.one2one();

    CSProcess[] procList = {
        new Producer(communicationChannel, SinglePortionGenerator::new),
        new Consumer(communicationChannel, SinglePortionGenerator::new)
    };

    Parallel parallel = new Parallel(procList);
    parallel.run();
  }
}
