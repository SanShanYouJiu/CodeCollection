package javaHighConcurrentDesign.chapter7.STM;

import akka.actor.UntypedActor;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

/**
 */
public class EmployeeActor extends UntypedActor {
    private Ref.View<Integer> count = STM.newRef(50);


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Coordinated) {
            final Coordinated c = (Coordinated) message;
            final int downCount = (Integer) c.getMessage();
            try {
                c.atomic(new Runnable() {
                    @Override
                    public void run() {
                        STM.increment(count, downCount);
                    }
                });
            } catch (Exception e) {

            }
        } else if ("GetCount".equals(message)) {
            getSender().tell(count.get(), getSelf());
        }else {
            unhandled(message);
        }
    }
}
