package main.actors.interfaces;

import main.buffer.interfaces.Buffer;

public interface ConsumerFactory {
  Consumer create(Buffer buffer, final long rngSeed);
}
