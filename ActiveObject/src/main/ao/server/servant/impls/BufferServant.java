package main.ao.server.servant.impls;

import main.ao.server.methodrequest.impls.PutRequest;
import main.ao.server.methodrequest.impls.TakeRequest;
import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.servant.interfaces.Servant;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.BoundedBufferWithOpsLimitFactory;
import main.buffer.interfaces.BufferOpsLimitReachedListener;

public class BufferServant extends BoundedSizeBufferWithOpsLimit implements BufferOpsLimitReachedListener {
  private final BoundedSizeBufferWithOpsLimit buffer;


  public BufferServant(final int bufferSize, final long maxOperations, BoundedBufferWithOpsLimitFactory bufferFactory) {
    super(bufferSize, maxOperations);
    buffer = bufferFactory.create(bufferSize, maxOperations, true);
    buffer.registerBufferOpsLimitReachedListener(this);
  }

  public boolean put(Object[] portion) {
    System.out.println();
    if (!operationLimitReached) {
      buffer.put(portion);
      ++completedOperations;
      return true;
    }
    return false;
  }

  public Object[] take(int portionSize) {
    if (!operationLimitReached) {
      ++completedOperations;
      return buffer.take(portionSize);
    } else {
      return null;
    }
  }

  @Override
  public boolean canTake(int portionSize) {
    return buffer.canTake(portionSize);
  }

  @Override
  public boolean canPut(int portionSize) {
    return buffer.canPut(portionSize);
  }

  @Override
  public void notifyOnOpsLimitReached() {
    block();
    System.out.println("Operations completed on BufferServant");
  }
}
