package main.buffer.impl;

import main.buffer.interfaces.BoundedBuffer;

import java.util.Arrays;

public final class CyclicBuffer
    extends BoundedBuffer {
  private final Object[] buffer;

  private int firstEmptyIndex = 0;
  private int firstOccupiedIndex = 0;
  private int occupiedCount = 0;

  public CyclicBuffer(final int size, boolean log) {
    super(size);
    this.buffer = new Object[size];
    this.log = log;
    Arrays.fill(buffer, null);
  }

  @Override
  public boolean put(Object[] objects) {
    for (Object object : objects) {
      buffer[firstEmptyIndex++] = object;
      firstEmptyIndex %= size;
    }
    occupiedCount += objects.length;
    if (log)
      System.out.println(
          "P:" + objects.length + ":" + firstOccupiedIndex + ":" + firstEmptyIndex + " " + this
      );
    return true;
  }

  @Override
  public Object[] take(final int n) {
    Object[] retArray = new Object[n];
    for (int i = 0; i < n; ++i) {
      retArray[i] = buffer[firstOccupiedIndex];
      buffer[firstOccupiedIndex++] = null;
      firstOccupiedIndex %= size;
    }
    occupiedCount -= n;
    if (log)
      System.out.println("T:" + n + ":" + firstOccupiedIndex + ":" + firstEmptyIndex + " " + this);
    return retArray;
  }

  @Override
  public boolean canPut(final int n) {
    return size - occupiedCount >= n;
  }

  @Override
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
