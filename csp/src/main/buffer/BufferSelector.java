package main.buffer;

import main.common.messages.RequestType;
import org.jcsp.lang.Parallel;

import java.util.Arrays;

abstract public class BufferSelector {
  protected Buffer[] mBufferSet;
  protected final int[] mBufferCapacities;
  protected final int[] mCurrentBufferCapacities;

  protected final boolean[] mBufferStatus; // true - obecnie realizuje operacje

  protected final int mNumberOfBuffers;

  public BufferSelector(Buffer[] bufferSet, int[] bufferCapacities) {
    mBufferSet = bufferSet;
    mNumberOfBuffers = bufferSet.length;

    if (mNumberOfBuffers != bufferCapacities.length) {
      mBufferCapacities = new int[mNumberOfBuffers];
      Arrays.fill(mBufferCapacities, bufferCapacities[0]);
    } else {
      mBufferCapacities = bufferCapacities;
    }

    mBufferStatus = new boolean[mNumberOfBuffers];
    Arrays.fill(mBufferStatus, false);

    mCurrentBufferCapacities = new int[mNumberOfBuffers];
    Arrays.fill(mCurrentBufferCapacities, 0);
  }

  protected boolean isProductionPossibleForBufferAt(int i, int resources) {
    return mBufferCapacities[i] - mCurrentBufferCapacities[i] >= resources;
  }

  protected boolean isConsumptionPossibleForBufferAt(int i, int resources) {
    return mCurrentBufferCapacities[i] >= resources;
  }

  abstract public BufferEntryPair getBufferForOperation(RequestType requestType, int resources);

  public void lockBuffer(int i) {
    mBufferStatus[i] = true;
  }

  public void unlockBuffer(int i) {
    mBufferStatus[i] = false;
  }
}
