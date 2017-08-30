package javaHighConcurrentDesign.chapter5.concurrent.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class SearchDemo {
    static int[] arr=new int[]{1,2,3,4,5,6,7,8,9,10,21,1213,12312,53,211,200};
    static ExecutorService pool = Executors.newCachedThreadPool();
    static final int Thread_Num = 2;
    static AtomicInteger result = new AtomicInteger(-1);


    public static int search(int searchValue, int beingPos, int endPos) {
        int i = 0;
        for (i = beingPos; i < endPos; i++) {
            if (result.get() >= 0) {
                return result.get();
            }
            if (arr[i] == searchValue) {
                //如果设置失败 表示其他线程已经先找到了
                if (!result.compareAndSet(-1, i)) {
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    public static  int pSearch(int searchValue) throws InterruptedException,ExecutionException {
        int subArrSize =arr.length/Thread_Num+1;
        List<Future<Integer>> re = new ArrayList<Future<Integer>>();
        for (int i = 0; i < arr.length; i+=subArrSize) {
            int end = i + subArrSize;
            if (end>arr.length) end = arr.length;
            re.add(pool.submit(new SearchTask(searchValue, i, end)));
        }
        for (Future<Integer> fu : re) {
            if (fu.get()>=0){
                return fu.get();
            }
        }
        return  -1;
    }

    public static class SearchTask implements Callable<Integer> {
        int begin, end, searchValue;

        public SearchTask(int searchValue, int begin, int end) {
            this.searchValue = searchValue;
            this.begin = begin;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            int re = search(searchValue, begin, end);
            return re;
        }
     }

    public static void main(String[] args) {
        try {
            int i = pSearch(200);
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
