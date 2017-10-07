package javaHighConcurrentDesign.chapter7.PSO;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 */
public class Bird extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private PsoValue pBest = null;
    private PsoValue gBest = null;
    private List<Double> velocity = new ArrayList<>(5);
    private List<Double> x = new ArrayList<>(5);
    private Random r = new Random();

    @Override
    public void preStart() throws Exception {
        for (int i = 0; i < 5; i++) {
            velocity.add(Double.NEGATIVE_INFINITY);
            x.add(Double.NEGATIVE_INFINITY);
        }
        //X1<=400
        x.set(1, (double) r.nextInt(401));

        //x2<=440-1.1*x1
        double max = 440 - 1.1 * x.get(1);
        if (max<0)max=0;
        x.set(2, r.nextDouble() * max);

        //x3<484-1.21*x1-1.1*x2
        max = 484 - 1.21 * x.get(1) - 1.1 * x.get(2);
        if (max<=0)max=0;
        x.set(3, r.nextDouble() * max);

        //x4<=532.4-1.331*x1-1.21*x2-1.1*x3
        max = 532.4 - 1.331 * x.get(1) - 1.21 * x.get(2) - 1.1 * x.get(3);
        if (max<=0)max=0;
        x.set(4, r.nextDouble() * max);

        double newFit = Fitness.fitness(x);
        pBest = new PsoValue(newFit, x);
        PBestMsg pBestMsg = new PBestMsg(pBest);
        ActorSelection selection = getContext().actorSelection("/user/masterbird");
        selection.tell(pBestMsg, getSelf());
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof GBestMsg) {
            gBest = ((GBestMsg) message).getValue();
            //更新速度
            for (int i = 0; i < velocity.size(); i++) {
                updateVelocity(i);
            }
            //更新位置
            for (int i = 0; i <x.size() ; i++) {
                updateX(i);
            }
            validateX();
            double newFit = Fitness.fitness(x);
            if (newFit > pBest.value) {
                pBest = new PsoValue(newFit, x);
                PBestMsg pBestMsg = new PBestMsg(pBest);
                getSender().tell(pBestMsg, getSelf());
            }
        }else {
            unhandled(message);
        }
    }



    public double updateVelocity(int i) {
        double v = Math.random() * velocity.get(i)
                + 2 * Math.random() * (pBest.getX().get(i) - x.get(i))
                + 2 * Math.random() * (gBest.getX().get(i) - x.get(i));
        v = v > 0 ? Math.min(v, 5) : Math.max(v, -5);
        velocity.set(i, v);
        return v;
    }

    public double updateX(int i) {
        double newX = x.get(i) + velocity.get(i);
        x.set(i, newX);
        return newX;
    }

    public void validateX(){
        if (x.get(1) > 400) {
            x.set(1, (double) r.nextInt(401));
        }

        //x2
        double max = 400 - 1.1 * x.get(1);
        if (x.get(2) > max || x.get(2)<0) {
            x.set(2, r.nextDouble() * max);
        }

        //x3
        max = 484 - 1.21 * x.get(1) - 1.1 * x.get(2);
        if (x.get(3) > max || x.get(3) < 0) {
            x.set(3, r.nextDouble() * max);
        }

        //x4
        max = 532.4 - 1.331 * x.get(1) - 1.21 * x.get(2) - 1.1 * x.get(3);
        if (x.get(4) > max || x.get(4) < 0) {
            x.set(4, r.nextDouble() * max);
        }
    }
}
