package main;

import main.actors.Consumer;
import main.actors.Producer;
import main.buffer.Controller;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt; // TO JEST INTERFEJS A NIE IMPLEMENTACJA
import org.jcsp.lang.Parallel;

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

    final One2OneChannelInt communicationChannel = Channel.one2oneInt();

    CSProcess[] procList = {
        new Producer(communicationChannel),
        new Consumer(communicationChannel)
    };

    Parallel parallel = new Parallel(procList);
    parallel.run();
  }
}
