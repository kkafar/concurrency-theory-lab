package main.ao.server.methodrequest.interfaces;

import main.ao.struct.impls.Promise;

abstract public class MethodRequest<T> {
  protected String methodName;
  protected Promise<T> promise;

  public MethodRequest(Promise<T> promise) {
    this.promise = promise;
  }

  public String getMethodName() {
    return methodName;
  }

  public Promise<T> getPromise() {
    return promise;
  }
}
