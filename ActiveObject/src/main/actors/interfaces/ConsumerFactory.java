package main.actors.interfaces;

import main.buffer.interfaces.BoundedBufferWithOpsLimit;

public interface ConsumerFactory {
  Consumer create(BoundedBufferWithOpsLimit buffer, final long initialRngSeed);
}
