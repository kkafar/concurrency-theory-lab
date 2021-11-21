package main.buffer.interfaces;

public interface SyncBufferFactory {
  SyncBuffer create(final int size, final boolean log);
}
