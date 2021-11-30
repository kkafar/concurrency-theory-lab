package main.actors;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
  private final One2OneChannelInt communicationChannel;

  public Consumer(final One2OneChannelInt in) {
    this.communicationChannel = in;
  }

  public void run() {
    System.out.println(communicationChannel.in().read());
  }
}
