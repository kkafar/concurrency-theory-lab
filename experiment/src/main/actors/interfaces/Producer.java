package main.actors.interfaces;

abstract public class Producer extends Thread {
  protected long executedTasks = 0;

  public abstract void put(Object[] objects);

  public long getExecutedTasks() {
    return executedTasks;
  }
}
