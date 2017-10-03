package javaHighConcurrentDesign.chapter6.defaultMethod;

/**
 * Created by www85 on 2017/9/11.
 */
public interface IAnimal {

    default void breath() {
        System.out.println("breath");
    }
}
