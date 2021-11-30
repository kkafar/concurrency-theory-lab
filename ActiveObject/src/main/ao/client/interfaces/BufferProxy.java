package main.ao.client.interfaces;

import main.ao.struct.interfaces.Promise;

public interface BufferProxy {
  Promise<Boolean> put(Object[] portion);
  Promise<Object[]> take(final int portionSize);

  int getSize();
}
