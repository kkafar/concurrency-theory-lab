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
    System.out.println("RRBS: Getting buffer for consumption");
    int index = mConsumptionBufferIndex;
    int processedBuffers = 0;

    while (
        processedBuffers < mNumberOfBuffers && !isConsumptionPossibleForBufferAt(index, resources)
    ) {
      index = (index + 1) % mNumberOfBuffers;
      ++processedBuffers;
    }

    if (processedBuffers < mNumberOfBuffers) {
      mConsumptionBufferIndex = index;
      System.out.println("RRBS: Accepting consumption for buffer " + index);
      return new BufferEntryPair(mBufferSet[mConsumptionBufferIndex], mConsumptionBufferIndex);
    } else {
      System.out.println("RRBS: Rejecting consumption");
      return null;
    }
  }

  private BufferEntryPair getBufferForProduction(final int resources) {
    System.out.println("RRBS: Getting buffer for production");
    int index = mProductionBufferIndex;
    int processedBuffers = 0;

    while (
        processedBuffers < mNumberOfBuffers && !isProductionPossibleForBufferAt(index, resources)
    ) {
      index = (index + 1) % mNumberOfBuffers;
      ++processedBuffers;
    }

    if (processedBuffers < mNumberOfBuffers) {
      mProductionBufferIndex = index;
      System.out.println("RRBS: Accepting production for buffer " + index);
      return new BufferEntryPair(mBufferSet[mProductionBufferIndex], mProductionBufferIndex);
    } else {
      System.out.println("RRBS: Rejecting production");
      return null;
    }
  }
}
