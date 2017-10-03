package javaHighConcurrentDesign.chapter6.parallel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 */
public class LongAdderDemo {
    private static final int MAX_THREADS = 3;//线程数
    private static final int TASK_COUNT = 3; //任务数
    private static final int TARGET_COUNT=10000000; //目标总数

    private AtomicLong account = new AtomicLong(0L);//无锁的原子操作

    private LongAdder lacount = new LongAdder();
    private long count=0;

    static CountDownLatch cdlsync = new CountDownLatch(TASK_COUNT);
    static CountDownLatch cdlatomic = new CountDownLatch(TASK_COUNT);
    static CountDownLatch cdladdr = new CountDownLatch(TASK_COUNT);

    protected  synchronized long inc(){ //有锁的加法
        return  ++count;
    }

    protected synchronized  long getCount(){ //有锁的操作
        return count;
    }

    public class  SyncThread implements Runnable{
        protected String name;
        protected long starttime;
        LongAdderDemo out;

        public SyncThread(LongAdderDemo o, long starttime) {
            out = o;
            this.starttime=starttime;
        }

        @Override
        public void run() {
            long v = out.getCount();
            while (v < TARGET_COUNT) { //在到达目标值之前 不停循环
                v = out.inc();
            }
            long endtime = System.currentTimeMillis();
            System.out.println("SynchThread spend:" + (endtime - starttime) + "ms" + " v=" + v);
            cdlsync.countDown();

        }
    }

    public void testSync() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
        long starttime = System.currentTimeMillis();
        SyncThread sync = new SyncThread(this, starttime);
        for (int i = 0; i < TASK_COUNT; i++) {
            exe.submit(sync);
        }
        cdlsync.await();
        exe.shutdown();

    }

    public class AtomicThread implements Runnable {
        protected String name;
        protected long starttime;

        public AtomicThread(long starttime) {
            this.starttime=starttime;
        }

        @Override
        public void run() {
            long v = account.get();
            while (v<TARGET_COUNT){
                v = account.incrementAndGet();//无锁的加法
            }
            long endtime =System.currentTimeMillis();
            System.out.println("AtomicThread spend"+(endtime-starttime)+"ms"+" v="+v);
            cdlatomic.countDown();
        }
    }

    public void testAtomic() throws InterruptedException{
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
        long starttime = System.currentTimeMillis();
        AtomicThread atomic = new AtomicThread(starttime);
        for (int i = 0; i < TASK_COUNT; i++) {
            exe.submit(atomic);
        }
        cdlatomic.await();
        exe.shutdown();
    }

    public class LongAdderThread implements Runnable{
      protected String name;
      protected long stattime;

        public LongAdderThread(long starttime) {
            this.stattime=starttime;
        }

        @Override
        public void run() {
            long v = lacount.sum();
            while (v < TARGET_COUNT) {
                lacount.increment();
                v = lacount.sum();
            }
            long endtime = System.currentTimeMillis();
            System.out.println("LongAdder spend:"+(endtime-stattime)+"ms"+" v="+v);
            cdladdr.countDown();
        }
    }

    public void testAtomicLong() throws InterruptedException{
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
        long starttime = System.currentTimeMillis();
        LongAdderThread longAddrThread = new LongAdderThread(starttime);
        for (int i = 0; i < TASK_COUNT; i++) {
            exe.submit(longAddrThread);
        }
        cdladdr.await();
        exe.shutdown();
    }


    public static void main(String[] args) throws InterruptedException {
        LongAdderDemo longAdderDemo = new LongAdderDemo();
        longAdderDemo.testSync();
        longAdderDemo.testAtomic();
        longAdderDemo.testAtomicLong();

    }
}
