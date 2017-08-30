package javaHighConcurrentDesign.chapter5;

public class Singleton {

    public static int STATUS = 1;

    private Singleton() {
        System.out.println("Singleton is create"+this.toString());
    }

    private static Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }


    public static void main(String[] args) {
        Runnable r= ()->{
            System.out.println(Singleton.STATUS);
            Singleton singleton = Singleton.getInstance();
            System.out.println(singleton);};
        Thread thread = new Thread(r);
        thread.start();

    }
}
