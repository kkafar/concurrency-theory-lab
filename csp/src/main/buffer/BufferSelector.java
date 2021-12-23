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
    return mBufferCapacities[i] - mCurrentBufferCapacities[i] >= resources && !isBufferLocked(i);
  }

  protected boolean isConsumptionPossibleForBufferAt(int i, int resources) {
    return mCurrentBufferCapacities[i] >= resources && !isBufferLocked(i);
  }

  abstract public BufferEntryPair getBufferForOperation(RequestType requestType, int resources);

  public void lockBuffer(int i, RequestType requestType) {
    System.out.println("BufferSelector: Lock buffer " + i);
    if (mBufferStatus[i]) {
      throw new IllegalStateException("BufferSelector: Buffer " + i + " already locked");
    }
    mBufferStatus[i] = true;
    if (requestType == RequestType.CONSUME) {
      mCurrentBufferCapacities[i]--;
    } else {
      mCurrentBufferCapacities[i]++;
    }
  }

  public void unlockBuffer(int i) {
    System.out.println("BufferSelector: Unlock buffer " + i);
    if (!mBufferStatus[i]) {
      throw new IllegalStateException("BufferSelector: Attempt to unlock not locked buffer " + i);
    }
    mBufferStatus[i] = false;
  }

  private boolean isBufferLocked(int i) {
    return mBufferStatus[i];
  }
}
