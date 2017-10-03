package javaHighConcurrentDesign.chapter6.defaultMethod;

/**
 */
public class Mule implements IHorse,IAnimal,IDonkey {
    @Override
    public void eat() {
        System.out.println("Mule eat");
     }

    @Override
    public void run() {
        IDonkey.super.run();
    }

    public static void main(String[] args) {
        Mule m = new Mule();
        m.run();
        m.breath();
    }

}
