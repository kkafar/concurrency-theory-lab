package main.ao.server.methodrequest.impls;

import main.ao.server.methodrequest.interfaces.ActorMethodRequest;
import main.ao.struct.impls.Promise;

public class TakeRequest extends ActorMethodRequest<Object[]> {
  public TakeRequest(final int portionSize, Promise<Object[]> promise) {
    super(promise);
    this.portionSize = portionSize;
  }
}
