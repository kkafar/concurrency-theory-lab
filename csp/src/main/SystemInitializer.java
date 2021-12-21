package main;

import main.actors.Consumer;
import main.actors.Producer;
import main.actors.UnitPortionGenerator;
import main.buffer.Buffer;
import main.buffer.Controller;
import org.jcsp.lang.Channel;

import java.util.ArrayList;
import java.util.List;

public class SystemInitializer {
  private final int mNProducers;
  private final int mNConsumers;
  private final int mNBuffers;
  private final List<Integer> mBufferCapacities;

  private final List<Producer> mProducers;
  private final List<Consumer> mConsumers;
  private final List<Buffer> mBuffers;
  private final Controller mController;

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

    mProducers = new ArrayList<>(mNProducers);
    mConsumers = new ArrayList<>(mNConsumers);
    mBuffers = new ArrayList<>(mNBuffers);

    initProducers();
    initConsumers();
    initBuffers();

    mController = new Controller();

    registerBuffersToController();
    registerActorsToController();
  }

  private void initProducers() {
    for (int i = 0; i < mNProducers; ++i) {
      mProducers.add(new Producer(Channel.one2one(), UnitPortionGenerator::new));
    }
  }

  private void initConsumers() {
    for (int i = 0; i < mNConsumers; ++i) {
      mConsumers.add(new Consumer(Channel.one2one(), UnitPortionGenerator::new));
    }
  }

  private void initBuffers() {
    if (mBufferCapacities.size() == 1) {
      for (int i = 0; i < mNBuffers; ++i) {
        mBuffers.add(new Buffer(mBufferCapacities.get(0), Channel.one2one()));
      }
    } else {
      for (int i = 0; i < mNBuffers; ++i) {
        mBuffers.add(new Buffer(mBufferCapacities.get(i), Channel.one2one()));
      }
    }
  }

  private void registerBuffersToController() {
    mBuffers.forEach(buffer -> {
      mController.registerBuffer(buffer, buffer.getChannel());
    });
  }

  private void registerActorsToController() {
    mConsumers.forEach(consumer -> mController.registerActor(consumer, consumer.getChannel()));
    mProducers.forEach(producer -> mController.registerActor(producer, producer.getChannel()));
  }
}
