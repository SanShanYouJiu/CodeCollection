package javaHighConcurrentDesign.chapter7.Agent;

import akka.actor.UntypedActor;
import akka.dispatch.Mapper;
import scala.concurrent.Future;


/**
 */
public class CounterActor extends UntypedActor {

    Mapper addMapper= new Mapper<Integer,Integer>(){
        @Override
        public Integer apply(Integer i) {
            return i+1;
        }
    };


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Integer) {
            for (int i = 0; i < 100011; i++) {
                //我希望能够知道future什么时候结束
                Future<Integer> f = AgentDemo.counterAgent.alter(addMapper);
                AgentDemo.futures.add(f);
            }
            getContext().stop(getSelf());
        }else
            unhandled(message);
    }
}
