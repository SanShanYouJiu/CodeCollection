package javaHighConcurrentDesign.chapter6.function;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * Created by www85 on 2017/9/11.
 */
public class Test1 {
    static int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9,10};
    public static void main(String[] args) {
        //for (int i : arr) {
        //    System.out.println(i);
        //}
        Arrays.stream(arr).map((x)->x=x+1).forEach(System.out::println);
        System.out.println();
        Arrays.stream(arr).forEach(System.out::println);
        Arrays.stream(arr).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        });

        Arrays.stream(arr).forEach((final int x)->{
            System.out.println(x);
        });
    }
}
