package main.buffer.interfaces;

public interface Buffer {
  void put(final Object[] objects);

  Object[] take(final int n) throws InterruptedException;

  int getSize();

  void setLog(final boolean log);
}
