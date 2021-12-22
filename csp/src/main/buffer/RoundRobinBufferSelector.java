package main.buffer;

import main.common.messages.RequestType;
import java.util.LinkedList;

public class RoundRobinBufferSelector extends BufferSelector {
  private int mProductionBufferIndex;
  private int mConsumptionBufferIndex;

  public RoundRobinBufferSelector(Buffer[] bufferSet, int[] bufferCapacities) {
    super(bufferSet, bufferCapacities);
    mConsumptionBufferIndex = 0;
    mProductionBufferIndex = 0;
  }

  @Override
  public BufferEntryPair getBufferForOperation(RequestType requestType, int resources) {
    if (requestType == RequestType.CONSUME) {
      return getBufferForConsumption(resources);
    } else {
      return getBufferForProduction(resources);
    }
  }

  private BufferEntryPair getBufferForConsumption(final int resources) {
    int index = mConsumptionBufferIndex;
    int processedBuffers = 0;

    while (
        processedBuffers < mNumberOfBuffers &&
        !mBufferStatus[index] &&
        isConsumptionPossibleForBufferAt(index, resources)
    ) {
      index = (index + 1) % mNumberOfBuffers;
      ++processedBuffers;
    }

    if (processedBuffers < mNumberOfBuffers) {
      mConsumptionBufferIndex = index;
      return new BufferEntryPair(mBufferSet[mConsumptionBufferIndex], mConsumptionBufferIndex);
    } else {
      return null;
    }
  }

  private BufferEntryPair getBufferForProduction(final int resources) {
    int index = mProductionBufferIndex;
    int processedBuffers = 0;

    while (
        processedBuffers < mNumberOfBuffers &&
        !mBufferStatus[index] &&
        isProductionPossibleForBufferAt(index, resources)
    ) {
      index = (index + 1) % mNumberOfBuffers;
      ++processedBuffers;
    }

    if (processedBuffers < mNumberOfBuffers) {
      mProductionBufferIndex = index;
      return new BufferEntryPair(mBufferSet[mProductionBufferIndex], mProductionBufferIndex);
    } else {
      return null;
    }
  }
}
