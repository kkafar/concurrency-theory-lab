package main.actors.interfaces;

import main.ao.client.interfaces.BufferProxy;
import main.buffer.interfaces.BoundedBufferWithOpsLimit;

public interface ProducerFactory {
  Producer create(BufferProxy buffer, final int extraTaskRepeats, final long initialRngSeed);
}
