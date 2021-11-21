package main.actors.interfaces;

import main.buffer.interfaces.BoundedBufferWithOpsLimit;

public interface ProducerFactory {
  Producer create(BoundedBufferWithOpsLimit buffer, final long initialRngSeed);
}
