package javaHighConcurrentDesign.chapter3;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by han on 2017/7/20.
 */
public class DivTask implements Runnable {
    int a,b;

    public DivTask(int a, int b) {
        this.a=a;
        this.b=b;
    }


    @Override
    public void run() {
        double re = a / b;//不能除0而少了一个输出
        System.out.println(re);
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        /**
         * 错误堆栈可以看到是在哪里提交的任务
         */
        for (int i = 0; i < 5; i++) {
            pools.execute(new DivTask(100, i));
        }
    }
}
