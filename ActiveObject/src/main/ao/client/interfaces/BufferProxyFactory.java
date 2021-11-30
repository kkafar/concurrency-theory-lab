package main.ao.client.interfaces;

import main.ao.struct.interfaces.PromiseFactory;

public interface BufferProxyFactory {
  BufferProxy create(final int bufferSize,
                     final long maxOperations,
                     final PromiseFactory promiseFactory,
                     final boolean log);
}
