package main.ao.client.interfaces;

import main.ao.struct.Promise;

public interface BufferProxy {
  Promise<Void> put(Object[] portion);
  Promise<Object[]> take(final int portionSize);
}
