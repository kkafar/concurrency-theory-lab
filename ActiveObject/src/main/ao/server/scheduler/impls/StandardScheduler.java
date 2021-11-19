package main.ao.server.scheduler.impls;

import main.ao.server.methodrequest.interfaces.MethodRequest;
import main.ao.server.scheduler.interfaces.Scheduler;
import main.ao.struct.impls.NonSynchronizedList;
import main.ao.struct.impls.SynchronizedList;
import main.ao.struct.interfaces.ActivationStruct;

public class StandardScheduler extends Scheduler {
  private final ActivationStruct awaitingList;
  private final ActivationStruct freshList;

  private MethodRequest request;

  public StandardScheduler() {
    super();
    awaitingList = new NonSynchronizedList();
    freshList = new SynchronizedList();
  }

  @Override
  protected void dispatch() {

  }

  @Override
  public void add(MethodRequest methodRequest) {
    if (active) {
      freshList.putBack(methodRequest);
    }
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
      if (!awaitingList.isEmpty()) {
        if (awaitingList.peekFirst().guard()) { // first top
          if (!awaitingList.getFirst().call()) {
            deactivate();
            break;
          }
        } else {
          while (!awaitingList.peekFirst().guard()) {
            request = freshList.getFirst();
            if (request.guard()) {
              if (!request.call()) {
                deactivate();
                break;
              }

            } else {
              awaitingList.putBack(request);
            }
          }
        }
      } else {
        request = freshList.getFirst();
        while (!request.guard()) {
          awaitingList.putFront(request);
          request = freshList.getFirst();
        }
        if (!request.call()) {
          deactivate();
          break;
        }
      }
    }
  }

}
