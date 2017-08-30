package javaHighConcurrentDesign.chapter5.concurrent.sort;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 希尔排序的并发实现
 */
public class ShellSortDemo {

    static int[] arr = {2, 3, 4, 5, 1, 23, 11, 20, 0};

    static ExecutorService pool = Executors.newCachedThreadPool();

    public static class ShellSortTask implements Runnable {
        int i=0;
        int h=0;
        CountDownLatch l;

        public ShellSortTask(int i, int h, CountDownLatch l) {
            this.i = i;
            this.h = h;
            this.l = l;
        }

        @Override
        public void run() {
            if (arr[i] < arr[i - h]) {
                int tmp = arr[i];
                int j =i-h;
                while (j >= 0 && arr[j]>tmp) {
                    arr[j + h] = arr[j];
                    j-=h;
                }
                arr[j+h]=tmp;
            }
            l.countDown();
        }
    }

    public static void pShellSort(int[] arr) throws InterruptedException{
        //计算出最大的h值
        int h=1;
        CountDownLatch latch = null;
        while (h <= arr.length / 3) {
            h=h*3+1;
        }
        while (h > 0) {
            System.out.println("h=" + h);
            if (h >= 4) {
                latch = new CountDownLatch(arr.length - h);
            }
            for (int i = h; i < arr.length; i++) {
                if (h >= 4) {
                    pool.execute(new ShellSortTask(i, h, latch));
                }else {
                    if (arr[i] < arr[i - h]) {
                        int tmp = arr[i];
                        int j = i - h;
                        while (j >= 0 && arr[j] > tmp) {
                            arr[j + h] = arr[j];
                            j-=h;
                        }
                        arr[j + h] = tmp;
                    }
                    System.out.println(Arrays.toString(arr));
                }
            }
            //等待线程排序完成 进入下一层排序
            latch.await();
            //计算出下一个h值
            h=(h-1)/3;
        }
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 5, 1, 23, 11, 20, 0};
        try {
            pShellSort(arr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
