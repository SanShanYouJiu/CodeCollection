package javaHighConcurrentDesign.chapter6.defaultMethod;

/**
 * Created by www85 on 2017/9/11.
 */
public interface IDonkey {
    default  void  run(){
        System.out.println("Donkey");
    }
}
