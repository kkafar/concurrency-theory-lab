package main.buffer;

import main.common.CompletedOperationCountTracker;
import main.common.messages.Answer;
import main.common.messages.AnswerType;
import main.common.messages.Request;
import main.common.messages.RequestType;
import org.jcsp.lang.*;
import org.jcsp.net.settings.Req;

import java.util.Arrays;

public class Controller implements CSProcess, CompletedOperationCountTracker {
  private int mCompletedOperations;

  private final AltingChannelInput[] mActorInputs;
  private final ChannelOutput[] mActorOutput;
  private final AltingChannelInput[] mBufferInputs;
  private final ChannelOutput[] mBufferOutputs;

  private final boolean[] mBufferStatus; // true - obecnie realizuje operacje
  private final int[] mCurrentBufferCapacities;

  private int mConsumptionBufferIndex;
  private int mProductionBufferIndex;
  private final int[] mBufferMaxCapacities;


  public Controller(AltingChannelInput[] actorInputs,
                    ChannelOutput[] actorOutputs,
                    AltingChannelInput[] bufferInputs,
                    ChannelOutput[] bufferOutputs,
                    int[] bufferMaxCapacities
  ) {
    mCompletedOperations = 0;
    mActorInputs = actorInputs;
    mActorOutput = actorOutputs;
    mBufferInputs = bufferInputs;
    mBufferOutputs = bufferOutputs;

    assert bufferInputs.length == bufferOutputs.length;

    mBufferStatus = new boolean[bufferInputs.length];
    mCurrentBufferCapacities = new int[bufferInputs.length];

    Arrays.fill(mBufferStatus, false);
    Arrays.fill(mCurrentBufferCapacities, 0);

    mConsumptionBufferIndex = 0;
    mProductionBufferIndex = 0;

    mBufferMaxCapacities = bufferMaxCapacities;
  }

  @Override
  public void run() {
    Guard[] guard = new Guard[mActorInputs.length + mBufferInputs.length];
    for (int i = 0; i < mActorInputs.length; ++i) {
      guard[i] = mActorInputs[i];
    }
    for (int i = 0; i < mBufferInputs.length; ++i) {
      guard[i + mActorInputs.length] = mBufferInputs[i];
    }

    Alternative alternative = new Alternative(guard);
    while (true) {
      int guardIndex = alternative.fairSelect();

      if (guardIndex < mActorInputs.length) { // mamy request od aktora
        Request request = (Request) mActorInputs[guardIndex].read();

        if (request.getType() == RequestType.PRODUCE) {
          int productionBuffer = findBufferForProduction();
          if (productionBuffer < 0) {
            mActorOutput[guardIndex].write(new Answer(AnswerType.NEGATIVE, null, null));
          } else {
            // rezerwujemy buffer
            mBufferStatus[productionBuffer] = true;
            mProductionBufferIndex = (productionBuffer + 1) % mBufferInputs.length;

            One2OneChannel communicationChannel = Channel.one2one();

            mBufferOutputs[productionBuffer].write(
                new Request(RequestType.PRODUCE, communicationChannel, request.getPortionSize())
            );
            mActorOutput[guardIndex].write(
                new Answer(AnswerType.POSITIVE, null, communicationChannel)
            );
          }
        } else if (request.getType() == RequestType.CONSUME) {
          int consumptionBuffer = findBufferForConsumption();
          if (consumptionBuffer < 0) {
            mActorOutput[guardIndex].write(new Answer(AnswerType.NEGATIVE, null, null));
          } else {
            // rezerwujemy buffer
            mBufferStatus[consumptionBuffer] = true;
            mConsumptionBufferIndex = (consumptionBuffer + 1) % mBufferInputs.length;

            One2OneChannel communicationChannel = Channel.one2one();

            mBufferOutputs[consumptionBuffer].write(
                new Request(RequestType.CONSUME, communicationChannel, request.getPortionSize())
            );

            mActorOutput[guardIndex].write(
                new Answer(AnswerType.POSITIVE, null, communicationChannel)
            );

          }
        } else {
          throw new UnsupportedOperationException("Unsupported request type");
        }
      } else {  // mamy reakcję bufora
        int i = guardIndex - mActorInputs.length;
        Answer answer = (Answer) mBufferInputs[i].read();
        if (answer.getAnswerType() == AnswerType.POSITIVE) {
          mBufferStatus[i] = false;
          ++mCompletedOperations;
          if (answer.getRequestType() == RequestType.CONSUME) {
            mCurrentBufferCapacities[i]--;
          } else {
            mCurrentBufferCapacities[i]++;
          }
        } else {
          System.out.println("Negative buffer answer");
        }
      }
    }
  }

  private int findBufferForConsumption() {
    int checkedElems = 0;
    int iter = mConsumptionBufferIndex;
    while (checkedElems < mBufferInputs.length) {
      if (mBufferStatus[iter] && mCurrentBufferCapacities[iter] > 0) {
        return iter;
      }
      iter = (iter + 1) % mBufferInputs.length;
      ++checkedElems;
    }

    return -1; // nie udało się znaleźć
  }

  private int findBufferForProduction() {
    int checkedElemns = 0;
    int iter = mProductionBufferIndex;
    while (checkedElemns < mBufferInputs.length) {
      if (mBufferStatus[iter] && mCurrentBufferCapacities[iter] < mBufferMaxCapacities[iter]) {
        return iter;
      }
      iter = (iter + 1) % mBufferInputs.length;
      ++checkedElemns;
    }
    return -1; // nie udało się znaleźć
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
