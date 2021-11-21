package main.buffer.interfaces;

public interface OperationLimitReachedEventListener {
  void notifyOnOlrEvent(OperationLimitReachedEventEmitter source);
}
