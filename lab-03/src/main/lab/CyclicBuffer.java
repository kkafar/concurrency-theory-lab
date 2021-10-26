package main.lab;

import java.util.Arrays;

public class CyclicBuffer {
  private final int size;
  private final Object[] buffer;
  private final boolean log;

  private int firstEmptyIndex = 0;
  private int firstOccupiedIndex = 0;
  private int occupiedCount = 0;

  public CyclicBuffer(final int size, boolean log) {
    this.size = size;
    this.buffer = new Object[size];
    this.log = log;
    Arrays.fill(buffer, null);
  }

  public CyclicBuffer(final int size) {
    this(size, false);
  }

  public int getSize() {
    return size;
  }

  public void put() {
    buffer[firstEmptyIndex++] = new Object();
    firstEmptyIndex %= size;
    occupiedCount += 1;
    if (log)
      System.out.println(
          "P:" + 1 + ":" + firstOccupiedIndex + ":" + firstEmptyIndex + " " + this
      );
  }

  public Object take() {
    Object ret = buffer[firstOccupiedIndex];
    buffer[firstOccupiedIndex++] = null;
    firstOccupiedIndex %= size;
    occupiedCount -= 1;
    if (log)
      System.out.println("T:" + 1 + ":" +firstOccupiedIndex + ":" + firstEmptyIndex + " " + this);
    return ret;
  }

  public boolean canPut() {
    return size - occupiedCount >= 1;
  }

  public boolean canTake() {
    return occupiedCount >= 1;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(size + 2).append('[');
    for (int i = 0; i < size; ++i) {
      if (buffer[i] != null) stringBuilder.append('+');
      else stringBuilder.append('_');
    }
    return stringBuilder.append(']').toString();
  }
}
