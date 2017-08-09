package javaHighConcurrentDesign.chapter4;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLockInterruptSolve extends  Thread{
    protected  Object tool;

    static ReentrantLock lock1 = new ReentrantLock();

    static ReentrantLock lock2 = new ReentrantLock();


    public DeadLockInterruptSolve(Object obj) {
        this.tool=obj;
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
                lock2.lockInterruptibly();
                Thread.sleep(500);

                try {
                    lock1.lockInterruptibly();
                    System.out.println("哲学家A开始吃饭了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (lock1.isHeldByCurrentThread())
                        lock1.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if (lock2.isHeldByCurrentThread())
                    lock2.unlock();
            }

        }
        if (tool == lock1) {
            try {
                lock1.lockInterruptibly();
                Thread.sleep(500);
                try {
                    lock2.lockInterruptibly();
                    System.out.println("哲学家B开始吃饭了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (lock2.isHeldByCurrentThread())
                        lock2.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if (lock1.isHeldByCurrentThread())
                    lock1.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLockInterruptSolve 哲学家A = new DeadLockInterruptSolve(lock1);
        DeadLockInterruptSolve 哲学家B = new DeadLockInterruptSolve(lock2);
        哲学家A.start();
        哲学家B.start();
        Thread.sleep(1000);
        哲学家B.interrupt();
    }
}
