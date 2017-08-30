package javaHighConcurrentDesign.chapter5.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 */
public class Consumer implements WorkHandler<PCData> {

    @Override
    public void onEvent(PCData event) throws Exception {
        System.out.println(Thread.currentThread().getName() + ":Event:--" +  event.get() * event.get() + "--");
    }

}
