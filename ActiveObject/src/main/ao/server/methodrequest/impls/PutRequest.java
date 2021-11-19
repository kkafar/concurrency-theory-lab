package main.ao.server.methodrequest.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.servant.impls.BufferServant;
import main.ao.struct.impls.Promise;

public class PutRequest extends MethodRequest<Boolean> {
  private final Object[] portion;

  public PutRequest(Object[] portion, BufferServant bufferServant, Promise<Boolean> promise) {
    super(bufferServant, promise);
    this.portion = portion;
  }

  @Override
  public boolean call() {
    if (bufferServant.put(portion)) {
      promise.resolve(true);
      return true;
    } else {
      promise.reject();
      return false;
    }
  }

  @Override
  public boolean guard() {
    return bufferServant.canPut(portion.length);
  }
}
