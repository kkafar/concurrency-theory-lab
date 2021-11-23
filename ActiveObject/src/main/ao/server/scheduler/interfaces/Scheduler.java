package main.ao.server.scheduler.interfaces;

import main.ao.server.methodrequest.interfaces.MethodRequest;

abstract public class Scheduler extends Thread {
  protected volatile boolean active;
  protected static volatile boolean created;

  public Scheduler() {
    if (created) {
      throw new IllegalStateException("Scheduler constuctor called for the second time");
    }
    active = false;
    created = true;
  }

  abstract public void add(MethodRequest methodRequest);

  abstract protected void dispatch();

  @Override
  abstract public void run();

  public void deactivate() {
    active = false;
  }

  @Override
  public void start() {
    System.out.println("Starting scheduler");
    super.start();
  }
}
