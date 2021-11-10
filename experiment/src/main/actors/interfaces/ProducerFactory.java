package main.actors.interfaces;

import main.buffer.interfaces.Buffer;

public interface ProducerFactory {
  Producer create(Buffer buffer, final long rngSeed);
}
