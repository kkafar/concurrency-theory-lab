package main.actors.interfaces;

import main.ao.client.interfaces.BufferProxy;

public interface ConsumerFactory {
  Consumer create(BufferProxy buffer, final long initialRngSeed);
}
