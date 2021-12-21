package main.buffer;

import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;

public class Controller implements CSProcess, CompletedOperationCountTracker {
  private int mCompletedOperations;

  public Controller() {
    mCompletedOperations = 0;
  }



  @Override
  public void run() {

  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
