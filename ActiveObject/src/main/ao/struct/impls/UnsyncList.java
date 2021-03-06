package main.ao.struct.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.struct.interfaces.ActivationStruct;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class UnsyncList implements ActivationStruct {
  private final LinkedList<MethodRequest> requests;
  private boolean cancelled;

  public UnsyncList() {
    requests = new LinkedList<>();
    cancelled = false;
  }

  @Override
  public void putFront(MethodRequest request) {
    requests.addFirst(request);
  }

  @Override
  public void putBack(MethodRequest request) {
//    System.out.println("PutBack in awaitingList");
    requests.addLast(request);
  }

  @Override
  public MethodRequest peekFirst() {
//    System.out.println("PeekFirst in awaitingList");
    return requests.peekFirst();
  }

  @Override
  public MethodRequest getFirst() {
//    System.out.println("GetFirst in awaitingList");
    return requests.pollFirst();
  }

  @Override
  public void removeFirst() {
    requests.removeFirst();
  }

  @Override
  public void removeLast() {
    requests.removeLast();
  }

  @Override
  public boolean isEmpty() {
    return requests.isEmpty();
  }

  @Override
  public void cancelAll() {
      cancelled = true;
      requests.forEach(request -> request.getPromise().reject());
      requests.clear();
  }
}
