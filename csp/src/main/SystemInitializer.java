package main;

import main.actors.Consumer;
import main.actors.Producer;
import main.actors.UnitPortionGenerator;
import main.buffer.Buffer;
import main.buffer.Controller;
import org.jcsp.lang.Channel;
import org.jcsp.lang.Parallel;

import java.util.ArrayList;
import java.util.List;

public class SystemInitializer {
  private final int mNProducers;
  private final int mNConsumers;
  private final int mNBuffers;
  private final List<Integer> mBufferCapacities;

  private final Producer[] mProducers;
  private final Consumer[] mConsumers;
  private final Buffer[] mBuffers;
  private final Controller mController;

  // kanały komunikacji


  public SystemInitializer(
      final int nProducers,
      final int nConsumers,
      final int nBuffers,
      final List<Integer> bufferCapacities
  ) {
    mNProducers = nProducers;
    mNConsumers = nConsumers;
    mNBuffers = nBuffers;
    mBufferCapacities = bufferCapacities;

    assert mBufferCapacities.size() == mNBuffers || mBufferCapacities.size() == 1;

    mProducers = new Producer[mNProducers];
    mConsumers = new Consumer[mNConsumers];
    mBuffers = new Buffer[mNBuffers];

    initProducers();
    initConsumers();
    initBuffers();

    mController = new Controller(mNProducers, mNConsumers, mNBuffers);

    registerBuffersToController();
    registerActorsToController();
  }

  private void initProducers() {
    for (int i = 0; i < mNProducers; ++i) {
    }
  }

  private void initConsumers() {
    for (int i = 0; i < mNConsumers; ++i) {
    }
  }

  private void initBuffers() {
    if (mBufferCapacities.size() == 1) {
      for (int i = 0; i < mNBuffers; ++i) {
      }
    } else {
      for (int i = 0; i < mNBuffers; ++i) {
      }
    }
  }

  private void registerBuffersToController() {
    mBuffers.forEach(buffer ->
      mController.registerBuffer(buffer, buffer.getChannel())
    );
  }

  private void registerActorsToController() {
    mConsumers.forEach(consumer -> mController.registerActor(consumer, consumer.getChannel()));
    mProducers.forEach(producer -> mController.registerActor(producer, producer.getChannel()));
  }

  public Parallel getSystem() {
    Parallel system = new Parallel();
    system.addProcess(mController);
    mConsumers.forEach(system::addProcess);
    mProducers.forEach(system::addProcess);
    mBuffers.forEach(system::addProcess);
    return system;
  }
}
