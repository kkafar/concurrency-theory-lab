package main;

public class Main {
  private static final int nProducers = 2;
  private static final int nConsumers = 2;
  private static final int nBuffers = 10;
  private static final int[] bufferCapacities = {10};

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
