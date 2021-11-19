package main.ao.client.impls;

import main.ao.client.interfaces.BufferProxy;
import main.ao.server.methodrequest.impls.PutRequest;
import main.ao.server.methodrequest.impls.TakeRequest;
import main.ao.struct.impls.Promise;

public class SharedBufferProxy implements BufferProxy {
//  public SharedBuffer()
  @Override
  public Promise<Boolean> put(Object[] portion) {
    Promise<Boolean> promise = new Promise<>();
    PutRequest request = new PutRequest(portion, promise);
    // TODO: register in scheduler
    return promise;
  }

  @Override
  public Promise<Object[]> take(int portionSize) {
    Promise<Object[]> promise = new Promise<>();
    TakeRequest request = new TakeRequest(portionSize, promise);
    // TODO: register in scheduler
    return promise;
  }
}
