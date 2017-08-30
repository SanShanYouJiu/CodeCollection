package javaHighConcurrentDesign.chapter5.blocingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 */
public class Consumer implements Runnable {
    private BlockingQueue<PCData> queue; //缓冲区

    private static final int SLEEPTIME = 1000;

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Consumer id=" + Thread.currentThread().getId());
        Random r = new Random(); //随机等待时间
        try {
            while (true) {
                PCData data = queue.take();//提取任务
                if (null != data) {
                    int re = data.getData() * data.getData();//计算评分
                    System.out.println(data.getData()+"*"+data.getData()+":"+ re);
                    Thread.sleep(r.nextInt(SLEEPTIME));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
