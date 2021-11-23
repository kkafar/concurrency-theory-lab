package main;

import main.actors.impl.*;
import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;
import main.buffer.impl.FourCondsBufferProxy;
import main.buffer.interfaces.BufferFactory;
import main.experiment.Experiment;
import main.buffer.impl.ThreeLocksBufferProxy;
import main.experiment.analyzer.ExperimentResultAnalyzer;
import main.experiment.log.LogOptions;
import main.experiment.log.LogOptionsBuilder;
import main.experiment.task.impl.StandardTask;
import main.experiment.task.impl.StandardTaskConfiguration;
import main.experiment.task.interfaces.TaskConfiguration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
  private static final int N_REPEATS = 10;
  private static final int RNG_SEED = 10;

  private static final String LOG_FILE_PATH = "/home/kkafara/studies/cs/5_term/twsp/lab/experiment/data/temp";

  private static final int[] N_PRODUCERS_ARR = {
      1, 3, 5, 7, 9
  };

  private static final int[] N_CONSUMERS_ARR = {
      1, 3, 5, 7, 9
  };

//  private static final int[] BUFFER_SIZE_ARR = {
//      5, 10, 15, 20, 25, 30, 35, 40, 45, 50
//  };

  private static final int[] BUFFER_SIZE_ARR = {
      10, 50, 100
  };

//  private static final int[] BUFFER_OPS_ARR = {
//      10000, 50000, 100000, 150000, 200000, 250000
//  };

  private static final int[] BUFFER_OPS_ARR = {
      20000
  };

  private static final ConsumerFactory[] CONSUMER_FACTORY_ARR = {
      RandomPortionConsumer::new,
      MinimalPortionConsumer::new,
      MaximumPortionConsumer::new
  };

  private static final ProducerFactory[] PRODUCER_FACTORY_ARR = {
      RandomPortionProducer::new,
      MinimalPortionProducer::new,
      MaximumPortionProducer::new
  };

  private static final BufferFactory[] BUFFER_FACTORY_ARR = {
      ThreeLocksBufferProxy::new,
      FourCondsBufferProxy::new
  };

  public static void main(String[] args) throws InterruptedException, IOException {
    conductExperiments(getExperiments(getTaskConfigurations()));
  }

  private static List<TaskConfiguration> getTaskConfigurations() {
    List<TaskConfiguration> taskConfigurations = new LinkedList<>();
    for (int nProd : N_PRODUCERS_ARR) {
      for (int nCons : N_CONSUMERS_ARR) {
        for (int bufSize : BUFFER_SIZE_ARR) {
          for (int bufOps : BUFFER_OPS_ARR) {
            taskConfigurations.add(new StandardTaskConfiguration(
                "TASK DESCRIPTION",
                nProd,
                nCons,
                bufSize,
                bufOps
            ));
          }
        }
      }
    }
    return taskConfigurations;
  }

  private static List<Experiment> getExperiments(List<TaskConfiguration> taskConfigurations) {
    List<Experiment> experiments = new LinkedList<>();
    Experiment experiment;
    for (ConsumerFactory consumerFactory : CONSUMER_FACTORY_ARR) {
      for (ProducerFactory producerFactory : PRODUCER_FACTORY_ARR) {
        for (BufferFactory bufferFactory : BUFFER_FACTORY_ARR) {
          experiment = new Experiment(
              bufferFactory,
              producerFactory,
              consumerFactory,
              StandardTask::new,
              RNG_SEED,
              N_REPEATS
          );
          experiment.registerAll(taskConfigurations);
          experiments.add(experiment);
        }
      }
    }
    return experiments;
  }

  private static void conductExperiments(List<Experiment> experiments) throws IOException {
    ExperimentResultAnalyzer analyzer = new ExperimentResultAnalyzer();
    LogOptions logOptions = new LogOptionsBuilder()
        .logExperiment()
        .logInsideTask()
        .build();
    for (Experiment experiment : experiments) {
      experiment.setLogOptions(logOptions);
      experiment.conduct();
//      analyzer.analyze(experiment.getResult());
      analyzer.analyzeToFile(LOG_FILE_PATH, experiment.getResult());
    }
  }
}
