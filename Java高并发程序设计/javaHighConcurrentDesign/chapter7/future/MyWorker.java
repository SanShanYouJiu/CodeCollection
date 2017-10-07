package javaHighConcurrentDesign.chapter7.future;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 */
public class MyWorker extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);



    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Integer) {
            int i = (int) message;
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){}
            getSender().tell(i * i, getSelf());
        }
        if (message == AskMain.Msg.DONE) {
            log.info("Stop working");
        }
        if (message == AskMain.Msg.CLOSE) {
            log.info("I will shutdown");
            getSender().tell(AskMain.Msg.CLOSE, getSelf());
            getContext().stop(getSelf());
        }else
            unhandled(message);
    }
}
