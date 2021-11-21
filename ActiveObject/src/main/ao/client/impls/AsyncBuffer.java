package main.ao.client.impls;

import main.ao.client.interfaces.BufferProxy;
import main.ao.server.methodrequest.impls.PutRequest;
import main.ao.server.methodrequest.impls.TakeRequest;
import main.ao.server.scheduler.impls.StandardScheduler;
import main.ao.server.scheduler.interfaces.Scheduler;
import main.ao.server.servant.impls.BufferServant;
import main.ao.struct.interfaces.Promise;
import main.ao.struct.interfaces.PromiseFactory;
import main.buffer.impl.CyclicBuffer;

public class AsyncBuffer implements BufferProxy {
  private final Scheduler scheduler;
  private final BufferServant bufferServant;
  private final PromiseFactory promiseFactory;

  public AsyncBuffer(final int bufferSize,
                     final long maxOperations,
                     final PromiseFactory promiseFactory,
                     final boolean log) {
    this.promiseFactory = promiseFactory;
    bufferServant = new BufferServant(bufferSize, maxOperations, CyclicBuffer::new, log);
    scheduler = new StandardScheduler();
    scheduler.setName("Scheduler Thread");
    scheduler.start();
  }

  @Override
  public Promise<Boolean> put(Object[] portion) {
    Promise<Boolean> promise = promiseFactory.create();
    PutRequest request = new PutRequest(portion, bufferServant, promise);
    scheduler.add(request);
    return promise;
  }

  @Override
  public Promise<Object[]> take(int portionSize) {
    Promise<Object[]> promise = promiseFactory.create();
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
}
