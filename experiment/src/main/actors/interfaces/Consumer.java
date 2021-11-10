package main.actors.interfaces;

abstract public class Consumer extends Thread {
  protected long executedTasks = 0;

  abstract public void take(final int n) throws InterruptedException;

  public long getExecutedTasks() {
    return executedTasks;
  }
}
