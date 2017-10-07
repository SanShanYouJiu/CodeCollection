package javaHighConcurrentDesign.chapter7.PSO;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 */
public class PSOMain {

 public static final  int BIRD_COUNT=100000;

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("psoSystem", ConfigFactory.load("sampledemo.conf"));
        system.actorOf(Props.create(MasterBird.class), "masterbird");
        for (int i = 0; i < BIRD_COUNT; i++) {
            system.actorOf(Props.create(Bird.class), "bird_" + i);
        }
    }
}
