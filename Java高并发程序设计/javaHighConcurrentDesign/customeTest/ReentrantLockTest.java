package javaHighConcurrentDesign.customeTest;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by www85 on 2017/7/31.
 */
public class ReentrantLockTest {

    private static Lock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private  static Condition condition2 = lock.newCondition();

    class conditionTest implements Runnable{

        @Override
        public void run() {
            lock.lock();
            try {
                condition1.await();
                System.out.println("线程完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        conditionTest conditionTest = reentrantLockTest.new conditionTest();
        ExecutorService es =new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new
                SynchronousQueue<Runnable>(),(r)->{  Thread t = new Thread(r);
            t.setDaemon(true);
            System.out.println("create " + t);
            return t;});

        Future fe = es.submit(conditionTest);
        fe.get();
        //Thread t = new Thread(conditionTest);
        //t.start();
        Thread.sleep(100);
        lock.lock();
        condition1.signal();
        lock.unlock();

    }
}
