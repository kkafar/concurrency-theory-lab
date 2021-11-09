package main.lab.task1.buffer;

public interface BufferFactory {
  public Buffer create(final int bufferSize, final long actions,  boolean log);
}
