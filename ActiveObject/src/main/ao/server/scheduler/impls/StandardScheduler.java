package main.ao.server.scheduler.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.scheduler.interfaces.Scheduler;
import main.ao.struct.impls.UnsyncList;
import main.ao.struct.impls.SyncList;
import main.ao.struct.interfaces.ActivationStruct;

public class StandardScheduler extends Scheduler {
  private final ActivationStruct awaitingList; // not synchronized
  private final ActivationStruct freshList; // synchronized

  private MethodRequest request;

  public StandardScheduler() {
    super();
    awaitingList = new UnsyncList();
    freshList = new SyncList();
  }

  @Override
  protected void dispatch() {
    if (!awaitingList.isEmpty()) {
      if (awaitingList.peekFirst().guard()) { // first top
        awaitingList.getFirst().call();
      } else {
        while (!awaitingList.peekFirst().guard()) { // we cant execute first from awaitingList
          request = freshList.getFirst();           // ==> take care of freshList
          if (request.guard()) {
            request.call();
          } else {
            awaitingList.putBack(request);          //  we do not need to synchronize! awaitingList is accessed
          }                                         // only from Scheduler Thread!
        }
      }
    } else {
      request = freshList.getFirst();
      while (!request.guard()) {
        awaitingList.putFront(request);
        request = freshList.getFirst();
      }
      request.call();
    }
  }

  @Override
  public void add(MethodRequest methodRequest) {
    System.out.println("Adding MethodRequest to ActivationStruct");
    freshList.putBack(methodRequest);
  }

  @Override
  public void deactivate() {
    active = false;
    awaitingList.cancelAll();
    freshList.cancelAll();
  }

  @Override
  public void run() {
    active = true;
    while (active) {
      dispatch();
    }
  }

}
