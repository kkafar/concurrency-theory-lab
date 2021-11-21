package main.buffer.interfaces;

public interface Buffer<DataType, PutRequestAnswerType, TakeRequestAnswerType> {
  /**
   * Puts objects given in array into the buffer
   *
   * Note that in some implementations this method assumes that requested number
   * of resources is available
   *
   * @param data objects to put into the buffer
   */
  PutRequestAnswerType put(final DataType[] data);

  /**
   * Takes exactly n instances of resource from the buffer
   *
   * Note that in some implementations this method assumes that requested number
   * of resources is available
   *
   * @param requestSize number of resources to take from the buffer
   * @return requested resources
   */
  TakeRequestAnswerType take (final int requestSize);

  /**
   * Returns maximal capacity of the buffer
   *
   * @return maximal capacity of the buffer
   */
  int getSize();
}
