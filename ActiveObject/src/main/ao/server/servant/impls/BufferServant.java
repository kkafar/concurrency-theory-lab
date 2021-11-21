package main.ao.server.servant.impls;

import main.buffer.interfaces.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class BufferServant extends BoundedBufferWithOpsLimit implements OperationLimitReachedEventEmitter {
  private final Buffer buffer;
  private final Set<OperationLimitReachedEventListener> listeners;


  public BufferServant(
      final int bufferSize,
      final long maxOperations,
      final BufferFactory bufferFactory,
      final boolean log) {
    super(bufferSize, maxOperations);
    buffer = bufferFactory.create(bufferSize, log);
    listeners = new HashSet<>(1);
  }

  @Override
  public boolean put(Object[] portion) {
    if (isBlocked()) {
      return false;
    }
    ++completedOperations;
    return buffer.put(portion);
  }

  @Override
  public Object[] take(int portionSize) {
    if (isBlocked()) {
      return null;
    }
    ++completedOperations;
    return buffer.take(portionSize);
  }

  @Override
  public boolean canTake(int portionSize) {
    return buffer.canTake(portionSize);
  }

  @Override
  public boolean canPut(int portionSize) {
    return buffer.canPut(portionSize);
  }

  @Override
  public boolean addOlrListener(OperationLimitReachedEventListener listener) {
    return listeners.add(listener);
  }

  @Override
  public boolean removeOlrListener(OperationLimitReachedEventListener listener) {
    return listeners.remove(listener);
  }
}
