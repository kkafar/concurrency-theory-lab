package main.tasks.prodcons;

public class CyclicBuffer {
  private int firstEmptyIndex;
  private int firstOccupiedIndex;
  private int currentCount;

  private final Object[] buffer;

  public CyclicBuffer(int size) {
    this.buffer = new Object[size];
    firstEmptyIndex = 0;
    firstOccupiedIndex = 0;
    currentCount = 0;
  }

  public void put(Object[] objects) {
  }
}
