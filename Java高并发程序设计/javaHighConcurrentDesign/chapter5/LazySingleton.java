package javaHighConcurrentDesign.chapter5;

public class LazySingleton {

    private LazySingleton() {
        System.out.println("LazySingleton is create");
    }

    private static LazySingleton instance = null;

    public static synchronized LazySingleton getInstance() {
        if (instance == null)
            instance = new LazySingleton();
        return instance;
    }

    public static void main(String[] args) {
        Runnable r = () -> {
            LazySingleton singleton = LazySingleton.getInstance();
            System.out.println(singleton);
        };
        Thread t = new Thread(r);
        t.start();
    }
}
