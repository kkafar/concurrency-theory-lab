package main.common;

public class OperationCountTracker {
  protected int mCompletedOperations;
  protected int mRejectedOperations;
  protected int mFailedOperations;

  public OperationCountTracker() {
    mCompletedOperations = 0;
    mRejectedOperations = 0;
    mFailedOperations = 0;
  }

  public int getNumberOfCompletedOperations() {
    return mCompletedOperations;
  }

  public int getNumberOfRejectedOperations() {
    return mRejectedOperations;
  }

  public int getNumberOfFailedOperations() {
    return mRejectedOperations;
  }

  public void reportCompletedOperation() {
    ++mCompletedOperations;
  }

  public void reportRejectedOperation() {
    ++mRejectedOperations;
  }

  public void reportFailedOperation() {
    ++mFailedOperations;
  }
}
