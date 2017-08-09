package javaHighConcurrentDesign.chapter4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockTimeLockSolve extends Thread {
    protected Object tool;

    static ReentrantLock lock1 = new ReentrantLock();

    static ReentrantLock lock2 = new ReentrantLock();


    public DeadLockTimeLockSolve(Object obj) {
        this.tool = obj;
        if (tool == lock1) {
            this.setName("哲学家A");
        }
        if (tool == lock2) {
            this.setName("哲学家B");
        }
    }

    @Override
    public void run() {
        if (tool == lock2) {
            try {
                if (lock2.tryLock(5, TimeUnit.SECONDS)) {
                    Thread.sleep(500);
                    if (lock1.tryLock(5, TimeUnit.SECONDS)) {
                        System.out.println("哲学家A开始吃饭了");
                    } else {
                        System.out.println("获取不到lock1锁");
                    }
                } else {
                    System.out.println("获取不到lock2锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
            }
        }
            if (tool == lock1) {
                try {
                    if (lock1.tryLock(5, TimeUnit.SECONDS)) {
                        Thread.sleep(500);
                        try {
                            if (lock2.tryLock(5, TimeUnit.SECONDS)) {

                                System.out.println("哲学家B开始吃饭了");
                            } else {
                                System.out.println("获取不到lock2锁");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("获取不到lock1锁");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (lock1.isHeldByCurrentThread()) {
                        lock1.unlock();
                    }
                    if (lock2.isHeldByCurrentThread()) {
                        lock2.unlock();
                    }
                }
            }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLockTimeLockSolve 哲学家A = new DeadLockTimeLockSolve(lock1);
        DeadLockTimeLockSolve 哲学家B = new DeadLockTimeLockSolve(lock2);
        哲学家A.start();
        哲学家B.start();
        Thread.sleep(1000);
    }
}

