package main.buffer.interfaces;

public interface BufferFactory {
  Buffer create(final int bufferSize, final long actions,  boolean log);
}
