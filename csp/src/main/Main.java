package main;

import main.actors.Consumer;
import main.actors.Producer;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt; // TO JEST INTERFEJS A NIE IMPLEMENTACJA
import org.jcsp.lang.Parallel;

public class Main {
  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    final One2OneChannelInt communicationChannel = new One2OneChannelInt();

    CSProcess[] procList = {
        new Producer(communicationChannel),
        new Consumer(communicationChannel)
    };

    Parallel parallel = new Parallel(procList);

  }
}
