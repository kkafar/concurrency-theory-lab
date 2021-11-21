package main.buffer.interfaces;

abstract public class SyncBuffer implements Buffer<Object, Boolean, Object[]> {
  protected boolean log = false;

  /**
   * Decide whether internal information should be logged
   *
   * @param log true if internal information should be logged
   */
  public void setLog(final boolean log) {
    this.log = log;
  }

  /**
   * Determines whether given count of resource is available to take from the buffer
   *
   * @param requestSize number of portions to be taken from the buffer
   * @return true if there are at least requestSize resources in the buffer
   */
  abstract public boolean canTake(final int requestSize);

  /**
   * Determines wheter given count of resource can be put into the buffer
   *
   * @param requestSize number of portions to be put into buffer
   * @return true if there are at least requestSize free spaces in the buffer
   */
  abstract public boolean canPut(final int requestSize);
}
