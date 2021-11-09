package main.lab.task1.buffer;

public class SynchronizedCyclicBufferNestedLocksFactory implements BufferFactory {
  public SynchronizedCyclicBufferNestedLocksFactory() {}

  @Override
  public Buffer create(int bufferSize, long actions, boolean log) {
    return new SynchronizedCyclicBuffer(bufferSize, actions, log);
  }
}
