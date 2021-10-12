package main.runnable;

public class CustomRunnable implements Runnable {
  static int raceConditionedVariable = 0;

  @Override
  public void run() {
    ++raceConditionedVariable;
    --raceConditionedVariable;
  }
}
