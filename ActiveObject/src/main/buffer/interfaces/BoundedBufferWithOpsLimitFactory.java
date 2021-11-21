package main.buffer.interfaces;

public interface BoundedBufferWithOpsLimitFactory {
  /**
   * Creates instance of buffer
   *
   * @param bufferSize maximum buffer capacity
   * @param actions maximum numbers of put/take operations allowed to execute on a buffer
   * @param log true if internal information should be logged
   * @return buffer instance
   */
  BoundedBufferWithOpsLimit create(final int bufferSize, final long actions, boolean log);
}
