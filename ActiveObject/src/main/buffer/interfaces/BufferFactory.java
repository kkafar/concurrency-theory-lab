package main.buffer.interfaces;

public interface BufferFactory {
  Buffer create(final int size, final boolean log);
}
