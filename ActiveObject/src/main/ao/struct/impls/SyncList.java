package main.ao.struct.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.struct.interfaces.ActivationStruct;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SyncList implements ActivationStruct {
  private final LinkedList<MethodRequest> requests;
  private final ReentrantLock lock;
  private final Condition emptyList;

  private boolean cancelled;

  public SyncList() {
    requests = new LinkedList<>();
    lock = new ReentrantLock(true);
    emptyList = lock.newCondition();
    cancelled = false;
  }

  @Override
  public void putFront(MethodRequest request) {
    lock.lock();
    try {
      requests.addFirst(request);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void putBack(MethodRequest request) {
//    if (cancelled) return;
    System.out.println("PutBack in freshList");
    lock.lock();
    try {
      requests.addLast(request);
    } finally {
      emptyList.signal();
      lock.unlock();
    }
  }

  @Override
  public void removeFirst() {
    lock.lock();
    try {
      requests.removeFirst();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void removeLast() {
    lock.lock();
    try {
      requests.removeLast();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public MethodRequest peekFirst() {
    System.out.println("Peeking first in freshList");
    lock.lock();
    try {
      return requests.peekFirst();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public MethodRequest getFirst() {
    System.out.println("Get first in freshList");
    lock.lock();
    try {
      MethodRequest ret = requests.pollFirst();
      while (ret == null) {
        emptyList.await();
        ret = requests.pollFirst();
      }
      return ret;
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      lock.unlock();
    }
    return null;
  }

  @Override
  public boolean isEmpty() {
//    if (!cancelled) {
      lock.lock();
      try {
        return requests.isEmpty();
      } finally {
        lock.unlock();
      }
//    }
//    return true;
  }

  @Override
  public void cancelAll() {
//    if (cancelled) return;
    lock.lock();
    try {
//      cancelled = true;
//      requests.forEach(request -> request.getPromise().reject());
//      requests.clear();
    } finally {
      emptyList.signalAll();
      lock.unlock();
    }
  }
}
