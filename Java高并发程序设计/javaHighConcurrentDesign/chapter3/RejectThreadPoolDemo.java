package javaHighConcurrentDesign.chapter3;

import java.util.concurrent.*;

public class RejectThreadPoolDemo {

 public static class MyTask implements Runnable{

     @Override
     public void run() {
         System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
         try {
             Thread.sleep(100);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
 }

    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        ExecutorService es =new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new
                LinkedBlockingDeque<>(10), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(r.toString() + "is discard");
            }
        });

        for (int i = 0; i < 20; i++) {
            es.submit(task);
            Thread.sleep(10);
        }
    }
}
