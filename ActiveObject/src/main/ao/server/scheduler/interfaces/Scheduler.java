package main.ao.server.scheduler.interfaces;

import main.ao.server.methodrequest.interfaces.MethodRequest;

abstract public class Scheduler extends Thread {
  protected volatile boolean active;

  public Scheduler() {
    active = false;
  }

  abstract public void add(MethodRequest methodRequest);

  abstract protected void dispatch();

  @Override
  abstract public void run();

  public void deactivate() {
    active = false;
  }
}
