package main.ao.server.scheduler.interfaces;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.struct.impls.SynchronizedPriorityQueue;
import main.ao.struct.interfaces.ActivationQueue;

abstract public class Scheduler extends Thread {
  protected final ActivationQueue taskQueue;

  public Scheduler() {
    taskQueue = new SynchronizedPriorityQueue();
  }

  public void register(MethodRequest methodRequest) {
    taskQueue.add(methodRequest);
  }

  abstract protected void dispatch();

  @Override
  abstract public void run();
}
