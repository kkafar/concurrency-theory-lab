package main.buffer.impl;

import main.buffer.interfaces.BoundedBuffer;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.Buffer;

import java.util.Arrays;

public final class CyclicBuffer extends BoundedSizeBufferWithOpsLimit {
  private final Object[] buffer;
  private boolean log;

  private int firstEmptyIndex = 0;
  private int firstOccupiedIndex = 0;
  private int occupiedCount = 0;

  public CyclicBuffer(final int size, final long maxOperations, boolean log) {
    super(size, maxOperations);
    this.buffer = new Object[size];
    this.log = log;
    Arrays.fill(buffer, null);
  }

  public void put(Object[] objects) {
    for (int i = 0; i < objects.length; ++i) {
      buffer[firstEmptyIndex++] = objects[i];
      firstEmptyIndex %= size;
    }
    occupiedCount += objects.length;
    ++completedOperations;
    if (completedOperations >= maxOperations) block();
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
    ++completedOperations;
    if (completedOperations >= maxOperations) block();
    if (log)
      System.out.println("T:" + n + ":" +firstOccupiedIndex + ":" + firstEmptyIndex + " " + this);
    return retArray;
  }

  public boolean canPut(final int n) {
    return size - occupiedCount >= n && !operationLimitReached;
  }

  public boolean canTake(final int n) {
    return occupiedCount >= n && !operationLimitReached;
  }

  public void setLog(final boolean log) {
    this.log = log;
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
