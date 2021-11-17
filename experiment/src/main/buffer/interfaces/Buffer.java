package main.buffer.interfaces;

public interface Buffer {
  void put(final Object[] objects);

  /**
   * Takes exactly n instances of resource from the buffer.
   *
   * Note that in some implementations this method assumes that requested number
   * of resources is available
   *
   * @param n number of resources to take from the buffer
   * @return requested resources
   * @throws InterruptedException
   */
  Object[] take(final int n) throws InterruptedException;

  /**
   * Get maximum capacity of the buffer
   *
   * @return maximum capacity of the buffer
   */
  int getSize();

  /**
   * Decide whether internal information should be logged
   *
   * @param log true if internal information should be logged
   */
  void setLog(final boolean log);
}
