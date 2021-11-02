package main.hometask.twoconds.buffer;

import main.hometask.twoconds.actors.Producer;
import main.hometask.twoconds.actors.Consumer;

import java.util.Arrays;

public class CyclicBuffer {
  private final int size;
  private final Object[] buffer;
  private final boolean log;

  private int firstEmptyIndex = 0;
  private int firstOccupiedIndex = 0;
  private int occupiedCount = 0;

  private final int[] statistics;

  public CyclicBuffer(final int size, final int threadPoolCount, boolean log) {
    this.size = size;
    this.buffer = new Object[size];
    this.log = log;
    Arrays.fill(buffer, null);
    this.statistics = new int[threadPoolCount];
    Arrays.fill(statistics, 0);
  }

  public CyclicBuffer(final int size, final int threadPoolCount) {
    this(size, threadPoolCount, false);
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

    Producer producer = (Producer) Thread.currentThread();
    ++statistics[producer.getID()];

    if (log) {
      System.out.println(
          "P:" + objects.length + ":" + firstOccupiedIndex + ":" + firstEmptyIndex + " " + this
      );
      System.out.println(Arrays.toString(statistics));
    }
  }

  public Object[] take(final int n) {
    Object[] retArray = new Object[n];
    for (int i = 0; i < n; ++i) {
      retArray[i] = buffer[firstOccupiedIndex];
      buffer[firstOccupiedIndex++] = null;
      firstOccupiedIndex %= size;
    }
    occupiedCount -= n;

    Consumer consumer = (Consumer) Thread.currentThread();
    ++statistics[consumer.getID()];

    if (log) {
      System.out.println("T:" + n + ":" +firstOccupiedIndex + ":" + firstEmptyIndex + " " + this);
      System.out.println(Arrays.toString(statistics));
    }
    return retArray;
  }

  public boolean canPut(final int n) {
    return size - occupiedCount >= n;
  }

  public boolean canTake(final int n) {
    return occupiedCount >= n;
  }

  public int[] getStatistics() {
    return statistics;
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
