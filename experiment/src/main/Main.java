package main;

import main.buffer.impl.FourCondsBufferProxy;
import main.experiment.Experiment;
import main.experiment.task.impl.StandardTask;
import main.actors.impl.RandomSizePortionConsumer;
import main.actors.impl.RandomSizePortionProducer;
import main.buffer.impl.ThreeLocksBufferProxy;
import main.experiment.task.impl.StandardTaskResult;

public class Main {
  private static final int N_PRODUCERS = 3;
  private static final int N_CONSUMERS = 3;
  private static final int BUFFER_SIZE = 10;
  private static final int BUFFER_OPS = 10;
  private static final int N_REPEATS = 1;
  private static final int RNG_SEED = 10;

  public static void main(String[] args) throws InterruptedException {
    Experiment experiment = new Experiment(
      ThreeLocksBufferProxy::new,
      RandomSizePortionProducer::new,
      RandomSizePortionConsumer::new,
      StandardTask::new,
      RNG_SEED,
      N_REPEATS
    );
  }
}
