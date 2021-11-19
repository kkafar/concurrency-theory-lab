package main.ao.server.scheduler.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.scheduler.interfaces.Scheduler;
import main.ao.struct.impls.SynchronizedPriorityQueue;
import main.ao.struct.interfaces.ActivationQueue;

public class StandardScheduler extends Scheduler {
  public StandardScheduler() {
    super();
  }

  @Override
  protected void dispatch() {
    MethodRequest request;
    while (true) {
      System.out.println("Dispatch");
      request = taskQueue.poll();
      if (request != null && request.guard()) {
        request.call();
      } else if (request != null) {
        this.register(request);
      }

    }
  }

  @Override
  public void run() {
    dispatch();
  }
}
