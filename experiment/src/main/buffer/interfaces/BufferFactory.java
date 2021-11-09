package main.buffer.interfaces;

public interface BufferFactory {
  public Buffer create(final int bufferSize, final long actions,  boolean log);
}
