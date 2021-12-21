package main.buffer;

import main.actors.Actor;
import main.common.CompletedOperationCountTracker;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

import java.util.ArrayList;

public class Controller implements CSProcess, CompletedOperationCountTracker {
  private int mCompletedOperations;

  private final ArrayList<One2OneChannel> mActorChannels;
  private final ArrayList<One2OneChannel> mBufferChannels;

  public Controller() {
    mCompletedOperations = 0;
    mActorChannels = new ArrayList<>();
    mBufferChannels = new ArrayList<>();
  }

  public void registerActor(Actor actor, One2OneChannel channel) {
    mActorChannels.add(channel);
  }

  public void registerBuffer(Buffer buffer, One2OneChannel channel) {
    mBufferChannels.add(channel);
  }



  @Override
  public void run() {

  }

  @Override
  public int getCompletedOperations() {
    return mCompletedOperations;
  }
}
