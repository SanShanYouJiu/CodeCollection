package javaHighConcurrentDesign.chapter7.Helloworld;

import akka.actor.UntypedActor;

/**
 * Created by www85 on 2017/10/3.
 */
public class Greeter extends UntypedActor {

  public static enum Msg{
      GREET,DONE;
  }

    @Override
    public void onReceive(Object msg)  {
        if (msg == Msg.GREET) {
            System.out.println("Hello world!");
            getSender().tell(Msg.DONE, getSelf());
        }else {
            unhandled(msg);
        }
    }
}
