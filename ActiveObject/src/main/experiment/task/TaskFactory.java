package main.experiment.task;

import main.actors.interfaces.ConsumerFactory;
import main.actors.interfaces.ProducerFactory;
import main.ao.client.interfaces.BufferProxyFactory;

public interface TaskFactory {
  /**
   * Returns new task instance
   *
   * @param description short description of task
   * @param numberOfProducers number of producer actors that should be started
   * @param numberOfConsumers number of consumers actors that should be started
   * @param producerFactory method returning instances of `Producer` abstract class
   * @param consumerFactory method returning instances of `Consumer` abstract class
   * @param bufferFactory method returning instances of `BufferProxy` interface
   * @param bufferSize maximum size of the buffer shared between actors
   * @param bufferOperationsBound number of operations that should be executed on buffer
   * @param startingRngSeed initial seed for random number generator
   * @param repeats number of times the task should be repeated
   * @return new, configured task instance
   */
  Task create(
      final String description,
      final int numberOfProducers,
      final int numberOfConsumers,
      final ProducerFactory producerFactory,
      final ConsumerFactory consumerFactory,
      final BufferProxyFactory bufferFactory,
      final int bufferSize,
      final long bufferOperationsBound,
      final long startingRngSeed,
      final int repeats
  );
}
