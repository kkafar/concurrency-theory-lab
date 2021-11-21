package main.buffer.interfaces;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface OperationLimitReachedEventEmitter {
  boolean addOlrListener(OperationLimitReachedEventListener listener);
  boolean removeOlrListener(OperationLimitReachedEventListener listener);
}
