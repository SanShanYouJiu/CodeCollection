package javaHighConcurrentDesign.chapter5.concurrent.compute;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by www85 on 2017/8/20.
 */
public class Div implements Runnable {

    public static BlockingQueue<Msg> bq = new LinkedBlockingDeque<>();

    @Override
    public void run() {
        while (true) {
            try {
                Msg msg = bq.take();
                msg.i=msg.i/2;
                System.out.println(msg.orgStr + "=" + msg.i);
            } catch (InterruptedException e) {

            }
        }
    }
}
