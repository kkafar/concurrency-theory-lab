package main.ao.struct;

import main.ao.server.interfaces.MethodRequest;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedPriorityQueue extends PriorityQueue<MethodRequest> {
  private final ReentrantLock lock;

  public SynchronizedPriorityQueue(final int initialCapacity, Comparator<? super MethodRequest> comparator) {
    super(initialCapacity, comparator);
    this.lock = new ReentrantLock(true);
  }

  public SynchronizedPriorityQueue() {
    super();
    this.lock = new ReentrantLock(true);
  }

  @Override
  public boolean add(MethodRequest methodRequest) {
    lock.lock();
    try {
      return super.add(methodRequest);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public MethodRequest poll() {
    lock.lock();
    try {
      return super.poll();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean isEmpty() {
    lock.lock();
    try {
      return super.isEmpty();
    } finally {
      lock.unlock();
    }
  }
}
