package main.ao.struct.interfaces;

import main.ao.server.methodrequest.interfaces.MethodRequest;

import java.util.PriorityQueue;

public interface ActivationStruct {
  void putFront(MethodRequest request);

  void putBack(MethodRequest request);

  void removeFirst();

  void removeLast();

  MethodRequest peekFirst();

  MethodRequest getFirst();

  boolean isEmpty();

  void cancelAll();
}
