package javaHighConcurrentDesign.chapter2;

/**
 * Created by han on 2017/7/17.
 */
public class opition2 {


    public static void main(String[] args) {
        Thread t = new Thread(new test());
         t.start();
    }


}
class  test implements Runnable{

    @Override
    public void run() {
        System.out.println("ces");
    }
}
