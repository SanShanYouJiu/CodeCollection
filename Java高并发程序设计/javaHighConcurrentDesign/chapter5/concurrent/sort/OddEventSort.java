package javaHighConcurrentDesign.chapter5.concurrent.sort;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 奇偶交换排序并发实现(:其实就是冒泡排序的改造
 */
public class OddEventSort {
    static int[] arr ;

    public static void setArr(int[] arr) {
        OddEventSort.arr = arr;
    }

    static  int exchFlag =1;
    static ExecutorService pool = Executors.newCachedThreadPool();

    static synchronized void setExchFlag(int v) {
        exchFlag = v;
    }

    static synchronized int getExchFlag(){
        return exchFlag;
    }


    public static class OddEventSortTask implements Runnable {
        int i;
        CountDownLatch latch;

        public OddEventSortTask(int i, CountDownLatch latch) {
            this.i = i;
            this.latch = latch;
        }

        @Override
        public void run() {
            if (arr[i] > arr[i + 1]) {
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i+1]=temp;
                setExchFlag(1);
            }
            latch.countDown();
        }
    }

        public  static void pOddEventSort(int[] arr) throws InterruptedException {
            int start=0;
            setArr(arr);
            while (getExchFlag() == 1 || start == 1) {
                setExchFlag(0);
                //偶数的数组长度 当start为1时 只有len/2-1个线程
                CountDownLatch latch = new CountDownLatch(arr.length / 2 - (arr.length % 2 == 0 ? start : 0));
                for (int i=start;i<arr.length-1;i+=2) {
                      pool.execute(new OddEventSortTask(i,latch));
                }
                //等待线程结束
                latch.await();
                if (start == 0) {
                    start=1;
                }else
                    start=0;
                System.out.println(Arrays.toString(arr));
            }
        }

    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 9, 10, 21, 1231, 0};
        try {
            pOddEventSort(arr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
