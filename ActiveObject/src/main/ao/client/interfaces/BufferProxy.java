package main.ao.client.interfaces;

import main.ao.struct.interfaces.Promise;
import main.buffer.interfaces.Buffer;

public interface BufferProxy extends Buffer<Object, Promise<Boolean>, Promise<Object[]>> {
  Promise<Boolean> put(Object[] portion);
  Promise<Object[]> take(final int portionSize);
}
