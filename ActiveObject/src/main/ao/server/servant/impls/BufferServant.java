package main.ao.server.servant.impls;

import main.ao.server.methodrequest.impls.PutRequest;
import main.ao.server.methodrequest.impls.TakeRequest;
import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.servant.interfaces.Servant;
import main.buffer.interfaces.BoundedSizeBufferWithOpsLimit;
import main.buffer.interfaces.BoundedBufferWithOpsLimitFactory;
import main.buffer.interfaces.Buffer;
import main.buffer.interfaces.BufferOpsLimitReachedListener;

import java.util.LinkedList;
import java.util.List;

public class BufferServant extends Buffer implements BufferOpsLimitReachedListener {
  private final BoundedSizeBufferWithOpsLimit buffer;
  private final List<BufferOpsLimitReachedListener> listeners;


  public BufferServant(final int bufferSize, final long maxOperations, BoundedBufferWithOpsLimitFactory bufferFactory) {
    buffer = bufferFactory.create(bufferSize, maxOperations, false);
    buffer.registerBufferOpsLimitReachedListener(this);
    listeners = new LinkedList<>();
  }

  public boolean put(Object[] portion) {
    return buffer.put(portion);
  }

  public Object[] take(int portionSize) {
    return buffer.take(portionSize);
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
  public int getSize() {
    return buffer.getSize();
  }

  public void addListener(BufferOpsLimitReachedListener listener) {
    listeners.add(listener);
  }

  @Override
  public void notifyOnOpsLimitReached() {
    listeners.forEach(listener -> listener.notifyOnOpsLimitReached());
  }
}
