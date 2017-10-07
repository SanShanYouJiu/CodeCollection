package javaHighConcurrentDesign.chapter7.supervisor;

import akka.actor.*;
import akka.japi.Function;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 */
public class Supervisor extends UntypedActor{

    private static SupervisorStrategy strategy =new OneForOneStrategy(3, Duration.create(1, TimeUnit.MINUTES), new Function<Throwable, SupervisorStrategy.Directive>() {
        @Override
        public SupervisorStrategy.Directive apply(Throwable t) throws Exception {
            if (t instanceof ArithmeticException) {
                System.out.println("meet ArithmeticException,just resume");
                return SupervisorStrategy.resume();
            } else if (t instanceof NullPointerException) {
                System.out.println("meet NullPointerException,restart");
                return SupervisorStrategy.restart();
            } else if (t instanceof IllegalArgumentException) {
                return SupervisorStrategy.stop();
            }else {
                return SupervisorStrategy.escalate();
            }
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof Props) {
            getContext().actorOf((Props) o, "restartActor");
        }else {
            unhandled(o);
        }
    }

    public static void customStrategy(ActorSystem system) {
        ActorRef a = system.actorOf(Props.create(Supervisor.class), "Supervisor");
        a.tell(Props.create(RestartActor.class), ActorRef.noSender());

        ActorSelection sel = system.actorSelection("akka://lifecycle/user/Supervisor/restartActor");

        for (int i = 0; i < 100; i++) {
            sel.tell(RestartActor.Msg.RESTART, ActorRef.noSender());
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("lifecycle", ConfigFactory.load("lifecycle.conf"));
        customStrategy(system);
    }
}
