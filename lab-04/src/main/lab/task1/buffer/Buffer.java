package main.lab.task1.buffer;

public interface Buffer {
  void put(final Object[] objects);
  Object[] take(final int n) throws InterruptedException;
  int getSize();
}
