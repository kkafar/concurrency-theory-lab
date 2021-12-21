package main.common.messages;

import org.jcsp.lang.One2OneChannel;

public class Answer {
  private final AnswerType mAnswerType;
  private final RequestType mRequestType;
  private final One2OneChannel mBus;

  public Answer(AnswerType answerType, RequestType requestType, One2OneChannel bus) {
    mAnswerType = answerType;
    mRequestType = requestType;
    mBus = bus;
  }

  public AnswerType getAnswerType() {
    return mAnswerType;
  }

  public RequestType getRequestType() {
    return mRequestType;
  }

  public One2OneChannel getCommunicationChannel() {
    return mBus;
  }
}
