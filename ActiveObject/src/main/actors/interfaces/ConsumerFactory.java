package main.actors.interfaces;

import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;

public interface ConsumerFactory {
  Consumer create(BoundedSizeBufferWithOpsLimit buffer, final long initialRngSeed);
}
