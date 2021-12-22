package main.common.messages;

public class Confirmation {
  private final OperationStatus mOperationStatus;

  public Confirmation(OperationStatus operationStatus) {
    mOperationStatus = operationStatus;
  }

  public OperationStatus getOperationStatus() {
    return mOperationStatus;
  }
}
