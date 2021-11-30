package main.ao.server.methodrequest.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.servant.impls.BufferServant;
import main.ao.struct.interfaces.Promise;

public class PutRequest extends MethodRequest<Boolean> {
  private final Object[] portion;

  public PutRequest(Object[] portion, BufferServant bufferServant, Promise<Boolean> promise) {
    super(bufferServant, promise);
    this.portion = portion;
  }

  @Override
  public boolean call() {
//    System.out.println("PullRequest call for portion: " + portion.length);
//    System.out.flush();
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
    //    System.out.println("PullRequest guard: " + result + " for portion: " + portion.length);
//    System.out.flush();
    return bufferServant.canPut(portion.length);
  }
}
