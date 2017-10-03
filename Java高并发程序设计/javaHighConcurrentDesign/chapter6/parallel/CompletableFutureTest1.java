package javaHighConcurrentDesign.chapter6.parallel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 */
public class CompletableFutureTest1 {
    public  static Integer calc(Integer para){
        //try {
            //模拟一个长时间的执行
            //Thread.sleep(1000);
        //} catch (InterruptedException e) {
        //}
        return para/2;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //final CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calc(50));
        //System.out.println(future.get());


        //CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50)).thenApply((i) -> Integer.toString(i))
        //        .thenApply((str)->"\""+str+"\"")
        //        .thenAccept(System.out::println);
        //fu.get();

        //CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
        //        .exceptionally(ex->{
        //            System.out.println(ex.toString());
        //            return 0;
        //        })
        //        .thenApply((i) -> Integer.toString(i))
        //        .thenApply((str)->"\""+str+"\"")
        //        .thenAccept(System.out::println);
        //fu.get();

        //CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
        //        .thenCompose((i) -> CompletableFuture.supplyAsync(() -> calc(i)))
        //        .thenApply((str) -> "\"" + str + "\"")
        //        .thenAccept(System.out::println);
        //fu.get();

        CompletableFuture<Integer> intFuture =CompletableFuture.supplyAsync(()->calc(50));
        CompletableFuture<Integer> intFuture2 =CompletableFuture.supplyAsync(()->calc(25));

        CompletableFuture<Void> fu =intFuture.thenCombine(intFuture2,(i,j)->(i+j)).thenApply((str)->"\""+str+"\"")
                .thenAccept(System.out::println);
        fu.get();
    }
}
