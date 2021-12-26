package main.common;

import main.common.messages.OperationStatus;

public class OperationCountTracker {
  protected int mCompletedOperations;
  protected int mRejectedOperations;
  protected int mFailedOperations;
  protected String mTrackedObjectDescription;

  public OperationCountTracker() {
    this("No description provided");
  }

  public OperationCountTracker(String description) {
    mCompletedOperations = 0;
    mRejectedOperations = 0;
    mFailedOperations = 0;
    mTrackedObjectDescription = description;
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

  public void reportOperation(OperationStatus operationStatus) {
    switch (operationStatus) {
      case SUCCEEDED -> ++mCompletedOperations;
      case FAILED -> ++mFailedOperations;
      default -> throw new IllegalArgumentException("Unsupported operation status");
    }
  }
}
