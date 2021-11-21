package main.ao.server.methodrequest.interfaces;

import main.ao.server.servant.impls.BufferServant;
import main.ao.struct.impls.UnsyncPromise;

abstract public class MethodRequest<T> implements Comparable<MethodRequest<T>> {
  protected UnsyncPromise<T> promise;

  protected int priority;

  protected final BufferServant bufferServant;

  public MethodRequest(BufferServant bufferServant, UnsyncPromise<T> promise) {
    System.out.println("MethodRequest instance created");
    this.priority = 1; // TODO
    this.promise = promise;
    this.bufferServant = bufferServant;
  }

  public UnsyncPromise<T> getPromise() {
    return promise;
  }

  public void setPriority(final int priority) {
    this.priority = priority;
  }

  public void increasePriority() {
    ++priority;
  }

  abstract public boolean call();

  abstract public boolean guard();

  public void cancel() {
    promise.reject();
  }

  @Override
  public int compareTo(MethodRequest<T> methodRequest) {
    return this.priority - methodRequest.priority;
  }
}
