package main.ao.struct.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.struct.interfaces.ActivationQueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedPriorityQueue extends ActivationQueue {
  private final ReentrantLock lock;

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
