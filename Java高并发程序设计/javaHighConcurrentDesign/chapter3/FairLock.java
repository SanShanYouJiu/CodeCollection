package javaHighConcurrentDesign.chapter3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by han on 2017/7/19.
 */
public class FairLock implements  Runnable {
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getId() + ":获得锁");
            }finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock rl = new FairLock();
        Thread t1 = new Thread(rl);
        Thread t2 = new Thread(rl);
        t1.start();t2.start();
    }
}
