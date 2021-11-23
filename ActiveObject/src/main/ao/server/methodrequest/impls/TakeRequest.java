package main.ao.server.methodrequest.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.servant.impls.BufferServant;
import main.ao.struct.interfaces.Promise;

public class TakeRequest extends MethodRequest<Object[]> {
  private final int portionSize;

  public TakeRequest(int portionSize, BufferServant bufferServant, Promise<Object[]> promise) {
    super(bufferServant, promise);
    this.portionSize = portionSize;
  }

  @Override
  public boolean call() {
//    System.out.println("TakeRequest call for portion: " + portionSize);
//    System.out.flush();
    Object[] res = bufferServant.take(portionSize);
    if (res == null){
      promise.reject();
      return false;
    } else {
      promise.resolve(res);
      return true;
    }
  }

  @Override
  public boolean guard() {
    //    System.out.println("TakeRequest guard: " + result + " for portion: " + portionSize);
    return bufferServant.canTake(portionSize);
  }
}
