package main.actors.interfaces;

import main.buffer.interfaces.Buffer;
import main.buffer.interfaces.SyncBuffer;

abstract public class ConsumerSync extends Consumer {
  public ConsumerSync(SyncBuffer buffer, long initialRngSeed) {
    super(buffer, initialRngSeed);
  }

  @Override
  public void take(final int requestSize) {
    buffer.take(requestSize);
    if (active) ++completedOperations;
  }
}
