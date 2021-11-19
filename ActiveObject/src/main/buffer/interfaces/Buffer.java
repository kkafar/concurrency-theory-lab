package main.buffer.interfaces;

abstract public class Buffer {
  /**
   * Puts objects given in array into the buffer
   *
   * Note that in some implementations this method assumes that requested number
   * of resources is available
   *
   * @param objects objects to put into the buffer
   */
  abstract public void put(final Object[] objects);

  /**
   * Takes exactly n instances of resource from the buffer
   *
   * Note that in some implementations this method assumes that requested number
   * of resources is available
   *
   * @param portionSize number of resources to take from the buffer
   * @return requested resources
   */
  abstract public Object[] take(final int portionSize);


  /**
   * Decide whether internal information should be logged
   *
   * @param log true if internal information should be logged
   */
  abstract public void setLog(final boolean log);

  /**
   * Determines whether given count of resource is available to take from the buffer
   *
   * @param portionSize number of portions to be taken from the buffer
   * @return true if there are at least portionSize resources in the buffer
   */
  abstract public boolean canTake(final int portionSize);

  /**
   * Determines wheter given count of resource can be put into the buffer
   *
   * @param portionSize number of portions to be put into buffer
   * @return true if there are at least portionSize free spaces in the buffer
   */
  abstract public boolean canPut(final int portionSize);
}
