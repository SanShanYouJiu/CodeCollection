package javaHighConcurrentDesign.chapter7.future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

/**
 */
public class AskMain {


    public static enum  Msg{
        DONE,CLOSE;
    }

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("askdemo", ConfigFactory.load("samplehello.conf"));
        ActorRef worker = system.actorOf(Props.create(MyWorker.class), "worker");
        ActorRef printer = system.actorOf(Props.create(Printer.class), "printer");
        system.actorOf(Props.create(WatchActor.class,worker), "watcher");

        // 等待future返回
        Future<Object> f =ask(worker,5,1500);
        int re = (int) Await.result(f, Duration.create(6, TimeUnit.SECONDS));
        System.out.println("return:" + re);

        //直接导向其他Actor pipe不会等待
        f = ask(worker, 6, 1500);
        pipe(f, system.dispatcher()).to(printer);

        worker.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }

}
