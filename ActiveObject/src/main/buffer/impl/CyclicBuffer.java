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
    assert canPut(objects.length) : "canPut:" + objects.length + " (entry)";
    assertState();

    for (Object object : objects) {
      buffer[firstEmptyIndex++] = object;
      firstEmptyIndex %= size;
    }
    occupiedCount += objects.length;
    if (log) {
      System.out.println(
          "P:" + objects.length + ":" + firstOccupiedIndex + ":" + firstEmptyIndex + ":" + occupiedCount + " " + this
      );
      System.out.flush();
    }
    assert canPut(objects.length) : "canPut: " + objects.length + " (exit)";
    assertState();
    return true;
  }

  @Override
  public Object[] take(final int requestSize) {
    assert canTake(requestSize) : "canTake: " + requestSize + " (entry)";
    assertState();

    Object[] retArray = new Object[requestSize];
    for (int i = 0; i < requestSize; ++i) {
      retArray[i] = buffer[firstOccupiedIndex];
      buffer[firstOccupiedIndex++] = null;
      firstOccupiedIndex %= size;
    }
    occupiedCount -= requestSize;
    if (log) {
      System.out.println("T:" + requestSize + ":" + firstOccupiedIndex + ":" + firstEmptyIndex + ":" + occupiedCount + " " + this);
    }
    System.out.flush();
    assert canTake(requestSize) : "canTake: " + requestSize + " (exit)";
    assertState();
    return retArray;
  }

  @Override
  public boolean canPut(final int requestSize) {
    return size - occupiedCount >= requestSize;
  }

  @Override
  public boolean canTake(final int requestSize) {
    return occupiedCount >= requestSize;
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

  private void assertState() {
    assert occupiedCount < size : "occupiedCount >= size";
    assert occupiedCount >= 0 : "occupiedCount < 0";
  }
}
