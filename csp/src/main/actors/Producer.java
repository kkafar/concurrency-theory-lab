package main.actors;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
  private final One2OneChannelInt communicationChannel;

  public Producer(final One2OneChannelInt out) {
    this.communicationChannel = out;
  }

  public void run() {
    communicationChannel.out().write(((int) (Math.random() * 100) + 1));
  }
}
