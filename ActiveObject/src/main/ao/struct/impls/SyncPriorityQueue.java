package main.ao.struct.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.struct.interfaces.ActivationStruct;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SyncPriorityQueue extends PriorityQueue<MethodRequest> implements ActivationStruct {
  private final ReentrantLock lock;
  private final Condition cond;

  public SyncPriorityQueue() {
    super();
    this.lock = new ReentrantLock(true);
    this.cond = lock.newCondition();
  }

  @Override
  public void putFront(MethodRequest methodRequest) {
    lock.lock();
    try {
      super.add(methodRequest);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void putBack(MethodRequest request) {

  }

  @Override
  public void removeFirst() {

  }

  @Override
  public void removeLast() {

  }

  @Override
  public MethodRequest getFirst() {
    lock.lock();
    try {
      return super.poll();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public MethodRequest peekFirst() {
    lock.lock();
    try {
      return super.peek();
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

  @Override
  public void cancelAll() {
    lock.lock();
    try {
      super.clear();
    } finally {
      lock.unlock();
    }
  }



}
