package main.common.messages;

public class Confirmation {
  private final OperationStatus mOperationStatus;
  private final int mBufferID;

  public Confirmation(OperationStatus operationStatus, int id) {
    mBufferID = id;
    mOperationStatus = operationStatus;
  }

  public Confirmation(OperationStatus operationStatus) {
    this(operationStatus, -1);
  }

  public OperationStatus getOperationStatus() {
    return mOperationStatus;
  }

  public int getBufferId() {
    return mBufferID;
  }
}
