package main;

import main.actors.Consumer;
import main.actors.Producer;
import main.actors.UnitPortionGenerator;
import main.buffer.Controller;
import org.jcsp.lang.*;

import java.util.List;

public class Main {
  private static final int nProducers = 2;
  private static final int nConsumers = 2;
  private static final int nBuffers = 2;
  private static final int[] bufferCapacities = {10, 10};

  public static void main(String[] args) {
    SystemInitializer systemCaseOne = new SystemInitializer(
        nProducers,
        nConsumers,
        nBuffers,
        bufferCapacities
    );
    systemCaseOne.getSystem().run();
  }
}
