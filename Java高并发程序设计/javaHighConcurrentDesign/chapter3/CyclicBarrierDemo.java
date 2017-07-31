package javaHighConcurrentDesign.chapter3;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

 public static class Soldier implements Runnable{
 private String soldier;
 private final CyclicBarrier cyclic;

     public Soldier(CyclicBarrier cyclic,String soldier) {
         this.soldier = soldier;
         this.cyclic = cyclic;
     }

     @Override
     public void run() {
         try {
             //等待所有士兵到齐
             cyclic.await();
             doWork();
             cyclic.await();
             //等待所有士兵完成工作
         } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (BrokenBarrierException e) {
             e.printStackTrace();
         }
     }

     private void doWork() {
         try {
             Thread.sleep(Math.abs(new Random().nextInt() % 10000));
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         System.out.println(soldier + ":任务完成");
     }

     public static class BarrierRun implements Runnable {
         boolean flag;
         int N;

         public BarrierRun(boolean flag, int n) {
             this.flag = flag;
             N = n;
         }

         @Override
         public void run() {
             if (flag) {
                 System.out.println("司令:[士兵" + N + "个，任务完成]");
             } else {
                 System.out.println("司令:[士兵" + N + "个,集合完成]");
                 flag=true;
             }
         }
     }

     public static void main(String[] args) {
         final  int N=10;
         Thread[] allSoldier = new Thread[N];
         boolean flag =false;
         CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
         //设置屏障点,主要是为了执行这个方法
         System.out.println("集合队伍！");
         for (int i = 0; i < N; i++) {
             System.out.println("士兵" + i + "报到！");
             allSoldier[i] = new Thread(new Soldier(cyclic, "士兵" + i));
             allSoldier[i].start();
             //下面就会抛出一个中断异常与9个BrokenBarrierException异常
             //if (i == 5) {
             //    allSoldier[0].interrupt();
             //}
         }
     }

 }
}
