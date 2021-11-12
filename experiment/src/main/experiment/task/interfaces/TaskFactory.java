package main.experiment.task.interfaces;

import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;

public interface TaskFactory {
  Task create(
      final String description,
      final int numberOfProducers,
      final int numberOfConsumers,
      final ProducerFactory producerFactory,
      final ConsumerFactory consumerFactory,
      final long initialRngSeed,
      final int repeats
  );
}
