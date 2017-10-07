package javaHighConcurrentDesign.chapter7.route;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 */
public class MyWorker extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static enum  Msg{
        WORKING,DONE,CLOSE;
    }


    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg == Msg.WORKING) {
            log.info("I am working");
        }
        if (msg == Msg.DONE) {
            log.info("Stop working");
        }
        if (msg == Msg.CLOSE) {
            log.info("I will shutdown");
            getSender().tell(Msg.CLOSE, getSelf());
            getContext().stop(getSelf());
        } else {
            unhandled(msg);
        }
    }

    public static void main(String[] args) {
        ActorSystem system =ActorSystem.create("inboxdemo", ConfigFactory.load("samplehello.conf"));
        ActorRef worker = system.actorOf(Props.create(MyWorker.class), "worker");

        final Inbox inbox = Inbox.create(system);
        inbox.watch(worker);
        inbox.send(worker, Msg.WORKING);
        inbox.send(worker, Msg.DONE);
        inbox.send(worker, Msg.CLOSE);
        while (true) {
            Object msg = inbox.receive(Duration.create(1, TimeUnit.SECONDS));
            if (msg == Msg.CLOSE) {
                System.out.println("My working is Closing");
            } else if (msg instanceof Terminated) {
                System.out.println("My worker is dead");
                system.shutdown();
                break;
            }else {
                System.out.println(msg);
            }
        }
    }
}
