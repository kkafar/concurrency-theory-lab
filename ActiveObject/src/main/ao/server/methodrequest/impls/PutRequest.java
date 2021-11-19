package main.ao.server.methodrequest.impls;

import main.ao.server.methodrequest.interfaces.ActorMethodRequest;
import main.ao.struct.impls.Promise;

public class PutRequest extends ActorMethodRequest<Boolean> {
  private final Object[] portion;

  public PutRequest(Object[] portion, Promise<Boolean> promise) {
    super(promise);
    this.portion = portion;
    this.portionSize = portion.length;
    this.methodName = "put";
  }

  public Object[] getPortion() {
    return portion;
  }
}
