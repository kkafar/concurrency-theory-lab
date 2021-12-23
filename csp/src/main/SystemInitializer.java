package main;

import main.actors.Consumer;
import main.actors.Producer;
import main.actors.UnitPortionGenerator;
import main.buffer.Buffer;
import main.buffer.BufferSelector;
import main.buffer.Controller;
import main.buffer.RoundRobinBufferSelector;
import main.common.HalfDuplexChannel;
import org.jcsp.lang.Parallel;

public class SystemInitializer {
  private final int mNumberOfProducers;
  private final int mNumberOfConsumers;
  private final int mNumberOfBuffers;
  private final int[] mBufferCapacities;

  private final Producer[] mProducers;
  private final Consumer[] mConsumers;
  private final Buffer[] mBuffers;
  private final Controller mController;

  private final HalfDuplexChannel[] mClientServerChannels;
  private final HalfDuplexChannel[] mBufferServerChannels;


  public SystemInitializer(
      final int nProducers,
      final int nConsumers,
      final int nBuffers,
      final int[] bufferCapacities
  ) {
    System.out.println("System initialization started");
    mNumberOfProducers = nProducers;
    mNumberOfConsumers = nConsumers;
    mNumberOfBuffers = nBuffers;
    mBufferCapacities = bufferCapacities;

    assert mBufferCapacities.length == mNumberOfBuffers || mBufferCapacities.length == 1;

    mProducers = new Producer[mNumberOfProducers];
    mConsumers = new Consumer[mNumberOfConsumers];
    mBuffers = new Buffer[mNumberOfBuffers];

    initProducers();
    initConsumers();
    initBuffers();

    BufferSelector bufferSelector = new RoundRobinBufferSelector(mBuffers, mBufferCapacities);

    mController = new Controller(bufferSelector);

    mClientServerChannels = new HalfDuplexChannel[mNumberOfProducers + mNumberOfConsumers];
    mBufferServerChannels = new HalfDuplexChannel[mNumberOfBuffers];

    createConnectionChannels();
    System.out.println("System initialization completed");
  }

  private void initProducers() {
    System.out.println("Initializing producers");
    for (int i = 0; i < mNumberOfProducers; ++i) {
      mProducers[i] = new Producer(UnitPortionGenerator::new);
    }
  }

  private void initConsumers() {
    System.out.println("Initializing consumers");
    for (int i = 0; i < mNumberOfConsumers; ++i) {
      mConsumers[i] = new Consumer(UnitPortionGenerator::new);
    }
  }

  private void initClients() {
    for (int i = 0; i < mNumberOfProducers; ++i) {
      mProducers[i] = new Producer(UnitPortionGenerator::new);
    }
    for (int i = 0; i < mNumberOfConsumers; ++i) {
      mConsumers[i] = new Consumer(UnitPortionGenerator::new);
    }
  }

  private void initBuffers() {
    System.out.println("Initializing buffers");
    if (mBufferCapacities.length == 1) {
      for (int i = 0; i < mNumberOfBuffers; ++i) {
        mBuffers[i] = new Buffer(mBufferCapacities[0], i);
      }
    } else {
      for (int i = 0; i < mNumberOfBuffers; ++i) {
        mBuffers[i] = new Buffer(mBufferCapacities[i], i);
      }
    }
  }

  private void createConnectionChannels() {
    System.out.println("Creating connections");
    for (int i = 0; i < mNumberOfProducers; ++i) {
      mClientServerChannels[i] = new HalfDuplexChannel(mProducers[i], mController);
      mProducers[i].setServerChannel(mClientServerChannels[i]);
    }
    for (int i = 0; i < mNumberOfConsumers; ++i) {
      mClientServerChannels[i + mNumberOfProducers] = new HalfDuplexChannel(mConsumers[i], mController);
      mConsumers[i].setServerChannel(mClientServerChannels[i + mNumberOfProducers]);
    }
    for (int i = 0; i < mNumberOfBuffers; ++i) {
      mBufferServerChannels[i] = new HalfDuplexChannel(mBuffers[i], mController);
      mBuffers[i].setServerChannel(mBufferServerChannels[i]);
    }
    mController.setClientChannels(mClientServerChannels);
    mController.setBufferChannels(mBufferServerChannels);
  }

  public Parallel getSystem() {
    Parallel system = new Parallel();

    system.addProcess(mController);
    system.addProcess(mProducers);
    system.addProcess(mConsumers);
    system.addProcess(mBuffers);

    return system;
  }
}
