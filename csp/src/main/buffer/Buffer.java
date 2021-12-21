package main.buffer;

import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;

public class Buffer implements CSProcess, CompletedOperationCountTracker {
  private int mCompletedOperations;

  public Buffer() {
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
