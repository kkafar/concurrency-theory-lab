package main.buffer.interfaces;

public interface BufferFactory {
  SyncBuffer create(final int size, final boolean log);
}
