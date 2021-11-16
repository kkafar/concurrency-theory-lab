package main;

import main.buffer.impl.FourCondsBufferProxy;
import main.experiment.Experiment;
import main.experiment.task.impl.StandardTask;
import main.actors.impl.RandomPortionConsumer;
import main.actors.impl.RandomPortionProducer;
import main.buffer.impl.ThreeLocksBufferProxy;
import main.experiment.task.impl.StandardTaskConfiguration;

public class Main {
  private static final int N_PRODUCERS = 3;
  private static final int N_CONSUMERS = 3;
  private static final int BUFFER_SIZE = 10;
  private static final int BUFFER_OPS = 10;
  private static final int N_REPEATS = 2;
  private static final int RNG_SEED = 10;

  private static final int[] N_PRODUCERS_ARR = {
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10
  };

  private static final int[] N_CONSUMERS_ARR = {
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10
  };

  private static final int[] BUFFER_SIZE_ARR = {
      5, 10, 15, 20, 25, 30, 35, 40, 45, 50
  };

  private static final int[] BUFFER_OPS_ARR = {
      1000, 5000, 10000, 15000, 20000, 25000
  };

  private static final boolean log = true;

  public static void main(String[] args) throws InterruptedException {
    conductThreeLocksExperiment();

    conductFourCondsExperiment();
  }

  private static void conductThreeLocksExperiment() {
    Experiment experiment = new Experiment(
        ThreeLocksBufferProxy::new,
        RandomPortionProducer::new,
        RandomPortionConsumer::new,
        StandardTask::new,
        RNG_SEED,
        N_REPEATS
    );
    experiment.register(
        new StandardTaskConfiguration(
            "TASK DESCRIPTION",
            N_PRODUCERS,
            N_CONSUMERS,
            BUFFER_SIZE,
            BUFFER_OPS
        )
    );
    experiment.setLog(log);
    experiment.conduct();
  }

  private static void conductFourCondsExperiment() {
    Experiment experiment = new Experiment(
        FourCondsBufferProxy::new,
        RandomPortionProducer::new,
        RandomPortionConsumer::new,
        StandardTask::new,
        RNG_SEED,
        N_REPEATS
    );
    experiment.register(
        new StandardTaskConfiguration(
            "TASK DESCRIPTION",
            N_PRODUCERS,
            N_CONSUMERS,
            BUFFER_SIZE,
            BUFFER_OPS
        )
    );
    experiment.setLog(log);
    experiment.conduct();
  }
}
