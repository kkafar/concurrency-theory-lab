package main.ao.client.interfaces;

import main.ao.struct.Promise;

public interface BufferProxy {
  Promise<Boolean> put(final int portionSize);
  Promise<Object[]> take(final int portionSize);
}
