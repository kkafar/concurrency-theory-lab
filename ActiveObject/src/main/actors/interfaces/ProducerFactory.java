package main.actors.interfaces;

import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;

public interface ProducerFactory {
  Producer create(BoundedSizeBufferWithOpsLimit buffer, final long initialRngSeed);
}
