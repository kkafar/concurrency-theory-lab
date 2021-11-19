package main.ao.server.methodrequest.interfaces;

import main.ao.struct.impls.Promise;

abstract public class MethodRequest<T> implements Comparable<MethodRequest<T>> {
  protected String methodName;
  protected Promise<T> promise;

  protected int priority;

  public MethodRequest(Promise<T> promise) {
    this.priority = 1; // TODO
    this.promise = promise;
  }

  public String getMethodName() {
    return methodName;
  }

  public Promise<T> getPromise() {
    return promise;
  }

  @Override
  public int compareTo(MethodRequest<T> methodRequest) {
    return this.priority - methodRequest.priority;
  }
}
