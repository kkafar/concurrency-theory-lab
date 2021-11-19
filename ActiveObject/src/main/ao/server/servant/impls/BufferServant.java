package main.ao.server.servant.impls;

import main.ao.server.methodrequest.impls.PutRequest;
import main.ao.server.methodrequest.impls.TakeRequest;
import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.servant.interfaces.Servant;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.BoundedBufferWithOpsLimitFactory;
import main.buffer.interfaces.BufferOpsLimitReachedListener;

public class BufferServant extends Servant implements BufferOpsLimitReachedListener {
  private final BoundedSizeBufferWithOpsLimit buffer;

  private boolean bufferOpsLimitReached;

  public BufferServant(final int bufferSize, final long actions, BoundedBufferWithOpsLimitFactory bufferFactory) {
    buffer = bufferFactory.create(bufferSize, actions, false);
    buffer.registerBufferOpsLimitReachedListener(this);
    bufferOpsLimitReached = false;
  }

  @Override
  public void dispatch(MethodRequest methodRequest) {
    if (bufferOpsLimitReached) {
      methodRequest.getPromise().reject();
    } else if (methodRequest.getMethodName().equals("put")) {
      PutRequest putRequest = (PutRequest) methodRequest;
      putRequest.getPromise().resolve(put(putRequest.getPortion()));
    } else if (methodRequest.getMethodName().equals("take")) {
      TakeRequest takeRequest = (TakeRequest) methodRequest;
      takeRequest.getPromise().resolve(take(takeRequest.getPortionSize()));
    } else {
      throw new IllegalArgumentException("Unknown method type: " + methodRequest.getMethodName());
    }
  }

  private boolean put(Object[] portion) {
    if (!bufferOpsLimitReached) {
      buffer.put(portion);
      return true;
    }
    return false;
  }

  private Object[] take(int portionSize) {
    if (!bufferOpsLimitReached) {
      return buffer.take(portionSize);
    } else {
      return null;
    }
  }

  @Override
  public void notifyOnOpsLimitReached() {
    bufferOpsLimitReached = true;
  }
}
