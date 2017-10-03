package javaHighConcurrentDesign.chapter6.defaultMethod;

/**
 */
public interface IHorse {
    void eat();

    default void run() {
        System.out.println("hourse run");
    }
}
