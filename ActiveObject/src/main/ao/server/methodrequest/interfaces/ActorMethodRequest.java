package main.ao.server.methodrequest.interfaces;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.struct.impls.Promise;

abstract public class ActorMethodRequest<T> extends MethodRequest<T> {
  protected int portionSize;

  public ActorMethodRequest(Promise<T> promise) {
    super(promise);
  }

  public int getPortionSize() {
    return portionSize;
  }
}
