package main.actors.interfaces;

import main.buffer.interfaces.Buffer;

public interface ConsumerFactory {
  Consumer create(Buffer buffer, final int extraTaskRepeats, final long initialRngSeed);
}
