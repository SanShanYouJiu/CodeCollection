package javaHighConcurrentDesign.chapter6.parallel;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 */
public class PrimeUtil {

    public static boolean isPrime(int number) {
        int  tmp =number;
        if (tmp < 2) {
            return  false;
        }
        for (int i=2;Math.sqrt(tmp) >=i;i++) {
            if (tmp % i == 0) {
                return  false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println( IntStream.range(1, 1000000).parallel().filter(PrimeUtil::isPrime).count());


        int[] arr = new int[10];
        Arrays.parallelSort(arr);

        Random r = new Random();
        Arrays.setAll(arr, (i) -> r.nextInt());

        Arrays.parallelSetAll(arr, (i) -> r.nextInt());

        Arrays.stream(arr).parallel().forEach(System.out::println);
    }
}
