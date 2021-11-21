package main.ao.server.methodrequest.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.servant.impls.BufferServant;
import main.ao.struct.impls.UnsyncPromise;

public class TakeRequest extends MethodRequest<Object[]> {
  private final int portionSize;

  public TakeRequest(int portionSize, BufferServant bufferServant, UnsyncPromise<Object[]> promise) {
    super(bufferServant, promise);
    this.portionSize = portionSize;
  }

  @Override
  public boolean call() {
    Object[] res = bufferServant.take(portionSize);
    if (res == null){
      promise.reject();
      return false;
    } else {
      promise.resolve(bufferServant.take(portionSize));
      return true;
    }
  }

  @Override
  public boolean guard() {
    return bufferServant.canTake(portionSize);
  }
}
