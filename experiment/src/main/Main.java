package main;

import main.buffer.impl.FourCondsBufferProxy;
import main.experiment.Experiment;
import main.experiment.task.impl.StandardTask;
import main.actors.impl.RandomSizePortionConsumer;
import main.actors.impl.RandomSizePortionProducer;
import main.buffer.impl.ThreeLocksBufferProxy;
import main.experiment.task.impl.StandardTaskConfiguration;
import main.experiment.task.impl.StandardTaskResult;

public class Main {
  private static final int N_PRODUCERS = 3;
  private static final int N_CONSUMERS = 3;
  private static final int BUFFER_SIZE = 10;
  private static final int BUFFER_OPS = 10;
  private static final int N_REPEATS = 2;
  private static final int RNG_SEED = 10;

  private static final boolean log = true;

  public static void main(String[] args) throws InterruptedException {
    conductThreeLocksExperiment();

    conductFourCondsExperiment();
  }

  private static void conductThreeLocksExperiment() {
    Experiment experiment = new Experiment(
        ThreeLocksBufferProxy::new,
        RandomSizePortionProducer::new,
        RandomSizePortionConsumer::new,
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
        RandomSizePortionProducer::new,
        RandomSizePortionConsumer::new,
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
