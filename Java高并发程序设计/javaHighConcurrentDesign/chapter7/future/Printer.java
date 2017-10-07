package javaHighConcurrentDesign.chapter7.future;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 */
public class Printer extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Integer) {
            System.out.println("Printer:" + message);
        }
        if (message == AskMain.Msg.DONE) {
            log.info("I will shutdown");
            getSender().tell(AskMain.Msg.CLOSE, getSelf());
            getContext().stop(getSelf());
        }else {
            unhandled(message);
        }
    }
}
