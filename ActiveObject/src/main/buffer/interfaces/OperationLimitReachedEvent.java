package main.buffer.interfaces;

public class OperationLimitReachedEvent {
  private final OperationLimitReachedEventEmitter source;
  public OperationLimitReachedEvent(
      final OperationLimitReachedEventEmitter source
  ) {
    this.source = source;
  }

  public OperationLimitReachedEventEmitter getSource() {
    return source;
  }
}
