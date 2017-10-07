package javaHighConcurrentDesign.chapter7.route;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.*;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class WatchActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Router router;

    {
        List<Routee> routees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ActorRef worker = getContext().actorOf(Props.create(MyWorker.class), "worker_" + i);
            getContext().watch(worker);
            routees.add(new ActorRefRoutee(worker));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof MyWorker.Msg) {
            router.route(msg, getSender());
        } else if (msg instanceof Terminated) {
            router = router.removeRoutee(((Terminated) msg).actor());
            System.out.println(((Terminated) msg).actor().path() + "is cosed ,routees =" + router.routees().size());
            if (router.routees().size() == 0) {
                System.out.println("Close system");
                RouteMain.flag.send(false);
                getContext().system().shutdown();
            } else {
                unhandled(this);
            }
        }
    }
}
