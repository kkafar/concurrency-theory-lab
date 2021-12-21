package main.actors;

import main.buffer.Buffer;
import main.common.messages.Answer;
import main.common.messages.AnswerType;
import main.common.messages.Request;
import main.common.messages.RequestType;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;

public class Consumer extends Actor {

  public Consumer(final AltingChannelInput serverInput,
    final ChannelOutput serverOutput,
    final AltingChannelInput bufferInput,
    final ChannelOutput bufferOutput,
    final PortionGeneratorFactory portionGeneratorFactory
  ) {
      super(serverInput, serverOutput, bufferInput, bufferOutput, portionGeneratorFactory);
  }

  public void run() {
    Request consumeRequest;
    Answer serverAnswer;
    Buffer buffer;

    while (true) {
      // tworzymy zapytanie
      consumeRequest = new Request(RequestType.CONSUME, null, mPortionGenerator.generatePortion());

      // Wysyłamy zapytanie o możliwość pobrania zasobów -- do skutku
      mActionPermissionGranted = false;
      while (!mActionPermissionGranted) {
        // wysłanie zapytania
        mServerOutput.write(consumeRequest);

        // oczekiwanie na odpowiedź
        serverAnswer = (Answer) mServerInput.read();

        if (serverAnswer.getAnswerType() == AnswerType.POSITIVE) {
          mActionPermissionGranted = true;
          ChannelInput bufferInput = serverAnswer.getCommunicationChannel().in();
          // nawiązanie komunikacji z buforem i przesłanie mu danych
          // TODO
        } else if (serverAnswer.getAnswerType() == AnswerType.NEGATIVE) {
          System.out.println("Negative response from server");
        } else {
          throw new UnsupportedOperationException("Unsupported server answer type");
        }
      }
      ++mCompletedOperations;
    }
  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
