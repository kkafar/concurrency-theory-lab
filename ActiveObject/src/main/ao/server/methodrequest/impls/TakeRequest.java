package main.ao.server.methodrequest.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.servant.impls.BufferServant;
import main.ao.struct.impls.Promise;

public class TakeRequest extends MethodRequest<Object[]> {
  private final int portionSize;

  public TakeRequest(int portionSize, BufferServant bufferServant, Promise<Object[]> promise) {
    super(bufferServant, promise);
    this.portionSize = portionSize;
  }

  @Override
  public void call() {
    Object[] res = bufferServant.take(portionSize);
    if (res == null){
      promise.reject();
    } else {
      promise.resolve(bufferServant.take(portionSize));
    }
  }

  @Override
  public boolean guard() {
    return bufferServant.canTake(portionSize);
  }
}