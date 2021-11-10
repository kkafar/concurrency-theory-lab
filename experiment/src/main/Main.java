package main;

import main.actors.impl.RandomSizePortionConsumer;
import main.actors.impl.RandomSizePortionProducer;
import main.buffer.impl.SyncThreeLocksBuffer;
import main.experiment.task.Task;
import main.experiment.task.TaskResult;

public class Main {
  private static final int N_PRODUCERS = 3;
  private static final int N_CONSUMERS = 3;
  private static final int N_REPEATS = 10;
  private static final int BUFFER_SIZE = 10;
  private static final int N_ITERATIONS = 20;
  private static final int N_ACTIONS = 100000;
  private static final int RNG_SEED = 10;

  public static void main(String[] args) throws InterruptedException {
    Task taskThreeLocks = new Task(
        "Solution with three locks",
        RandomSizePortionProducer::new,
        RandomSizePortionConsumer::new,
        SyncThreeLocksBuffer::new,
        RNG_SEED
    ).configure(
        N_PRODUCERS,
        N_CONSUMERS,
        N_REPEATS,
        BUFFER_SIZE
    ).setBufferLog(
        true
    );
    taskThreeLocks.conduct();

    TaskResult experimentThreeLocksResult = taskThreeLocks.getTaskResult();
  }
}
