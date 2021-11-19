package main.ao.client.impls;

import main.ao.client.interfaces.BufferProxy;
import main.ao.server.methodrequest.impls.PutRequest;
import main.ao.server.methodrequest.impls.TakeRequest;
import main.ao.server.scheduler.impls.StandardScheduler;
import main.ao.server.scheduler.interfaces.Scheduler;
import main.ao.server.servant.impls.BufferServant;
import main.ao.struct.impls.Promise;
import main.buffer.impl.CyclicBuffer;
import main.buffer.interfaces.BufferOpsLimitReachedListener;

public class SharedBufferProxy implements BufferProxy, BufferOpsLimitReachedListener {
  private final Scheduler scheduler;
  private final BufferServant bufferServant;

  public SharedBufferProxy(final int bufferSize, final long maxOperations) {
    bufferServant = new BufferServant(bufferSize, maxOperations, CyclicBuffer::new);
    bufferServant.addListener(this);
    scheduler = new StandardScheduler();
    scheduler.setName("Scheduler Thread");
    scheduler.start();
  }

  @Override
  public Promise<Boolean> put(Object[] portion) {
    Promise<Boolean> promise = new Promise<>();
    PutRequest request = new PutRequest(portion, bufferServant, promise);
    scheduler.add(request);
    return promise;
  }

  @Override
  public Promise<Object[]> take(int portionSize) {
    Promise<Object[]> promise = new Promise<>();
    TakeRequest request = new TakeRequest(portionSize, bufferServant, promise);
    scheduler.add(request);
    return promise;
  }

  public Scheduler getScheduler() {
    return scheduler;
  }

  @Override
  public int getSize() {
    return bufferServant.getSize();
  }

  @Override
  public void notifyOnOpsLimitReached() {
    scheduler.deactivate();
  }
}
