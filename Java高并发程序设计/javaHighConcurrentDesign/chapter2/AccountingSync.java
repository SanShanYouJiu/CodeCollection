package javaHighConcurrentDesign.chapter2;

/**
 * Created by www85 on 2017/7/26.
 */
public class AccountingSync implements Runnable {
    static AccountingSync instance = new AccountingSync();
    static volatile int i = 0;

    public     synchronized   void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            //synchronized (instance) {
                increase();
            //}
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}