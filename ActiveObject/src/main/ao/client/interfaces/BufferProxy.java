package main.ao.client.interfaces;

import main.ao.struct.impls.UnsyncPromise;

public interface BufferProxy {
  UnsyncPromise<Boolean> put(Object[] portion);
  UnsyncPromise<Object[]> take(final int portionSize);

  int getSize();
}
