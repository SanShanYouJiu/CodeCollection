package javaHighConcurrentDesign.chapter3;

/**
 * Created by www85 on 2017/7/30.
 */
public class DivTask2 implements Runnable {
    int a,b;

    public DivTask2(int a, int b) {
        this.a=a;
        this.b=b;
    }

    @Override
    public void run() {
        double re = a / b;
        System.out.println(re);
    }

    public static void main(String[] args) {

    }
}
