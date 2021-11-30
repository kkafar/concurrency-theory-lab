package main;

import main.actors.impls.MaximumPortionConsumer;
import main.actors.impls.MinimalPortionConsumer;
import main.actors.impls.MaximumPortionProducer;
import main.actors.impls.MinimalPortionProducer;
import main.actors.impls.RandomPortionConsumer;
import main.actors.impls.RandomPortionProducer;
import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;

import main.ao.client.impls.AsyncBuffer;
import main.ao.client.interfaces.BufferProxyFactory;

import main.experiment.Experiment;
import main.experiment.analyzer.ExperimentResultAnalyzer;
import main.experiment.task.StandardTask;
import main.experiment.task.StandardTaskConfiguration;
import main.experiment.task.TaskConfiguration;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
  private static final int N_REPEATS = 10;
  private static final int RNG_SEED = 10;

  private static final String LOG_FILE_PATH = "/home/kkafara/studies/cs/5_term/twsp/lab/ActiveObject/data/temp";
  private static final String DATA_DIR = "/home/kkafara/studies/cs/5_term/twsp/lab/data";
  private static final String DATA_DIR_CONF = DATA_DIR + "/config";

//  private static final int[] N_PRODUCERS_ARR = {
//      1, 2, 3, 4, 5, 6, 7, 8, 9, 10
//  };
//
//  private static final int[] N_CONSUMERS_ARR = {
//      1, 2, 3, 4, 5, 6, 7, 8, 9, 10
//  };
private static final int[] N_PRODUCERS_ARR = {
    1, 3, 5, 7
};

  private static final int[] N_CONSUMERS_ARR = {
      1, 3, 5, 7
  };

  private static final int[] BUFFER_SIZE_ARR = {
      10, 50
  };

  private static final int[] BUFFER_OPS_ARR = {
      15000
  };

  private static final int[] EXTRA_TASK_REPEATS = {
      50, 100, 250, 500
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

  private static final BufferProxyFactory[] BUFFER_FACTORY_ARR = {
      AsyncBuffer::new
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
            for (int taskRepeats : EXTRA_TASK_REPEATS){
              taskConfigurations.add(new StandardTaskConfiguration(
                  "TASK DESCRIPTION",
                  nProd,
                  nCons,
                  bufSize,
                  bufOps,
                  taskRepeats
              ));
            }
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
        for (BufferProxyFactory bufferFactory : BUFFER_FACTORY_ARR) {
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
//    LogOptions logOptions = new LogOptionsBuilder()
//        .logExperiment()
//        .logInsideTask()
//        .build();
    for (Experiment experiment : experiments) {
//      experiment.setLogOptions(logOptions);
      experiment.conduct();
//      analyzer.analyze(experiment.getResult());
      analyzer.analyzeToFile(resolveDataFile(), experiment.getResult());
    }
  }

  private static String resolveDataFile() throws IOException {
    StringBuilder dataFile = new StringBuilder().append(DATA_DIR).append("/data-");
    File config = new File(DATA_DIR_CONF);

    char[] readBuffer = new char[64];
    Arrays.fill(readBuffer, '$');

    int charsRead;
    try (FileReader configReader = new FileReader(config)) {
      charsRead = configReader.read(readBuffer);
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }

    StringBuilder numberBuilder = new StringBuilder(charsRead);

    for (int i = 0; i < charsRead; ++i) {
      numberBuilder.append(readBuffer[i]);
    }

    String number = numberBuilder.toString();

    dataFile.append(number).append(".txt");

    // zwiększenie licznika w pliku

    int numberAsInt = Integer.parseInt(number);

    String incrementedNumber = Integer.toString(++numberAsInt);

    try (FileWriter configWriter = new FileWriter(config)) {
      configWriter.write(incrementedNumber);
    }

    return dataFile.toString();
  }
}
