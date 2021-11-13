package main.experiment.task.interfaces;

import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;
import main.buffer.interfaces.BufferFactory;

public interface TaskFactory {
  Task create(
      final String description,
      final int numberOfProducers,
      final int numberOfConsumers,
      final ProducerFactory producerFactory,
      final ConsumerFactory consumerFactory,
      final BufferFactory bufferFactory,
      final int bufferSize,
      final long bufferOperationsBound,
      final long startingRngSeed,
      final int repeats
  );
}
