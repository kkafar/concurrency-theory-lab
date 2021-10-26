package main.labtask2.buffer;

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

  public void put(Object[] objects) {
    for (int i = 0; i < objects.length; ++i) {
      buffer[firstEmptyIndex++] = objects[i];
      firstEmptyIndex %= size;
    }
    occupiedCount += objects.length;
    if (log)
      System.out.println(
          "P:" + objects.length + ":" + firstOccupiedIndex + ":" + firstEmptyIndex + " " + this
      );
  }

  public Object[] take(final int n) {
    Object[] retArray = new Object[n];
    for (int i = 0; i < n; ++i) {
      retArray[i] = buffer[firstOccupiedIndex];
      buffer[firstOccupiedIndex++] = null;
      firstOccupiedIndex %= size;
    }
    occupiedCount -= n;
    if (log)
      System.out.println("T:" + n + ":" +firstOccupiedIndex + ":" + firstEmptyIndex + " " + this);
    return retArray;
  }

  public boolean canPut(final int n) {
    return size - occupiedCount >= n;
  }

  public boolean canTake(final int n) {
    return occupiedCount >= n;
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
