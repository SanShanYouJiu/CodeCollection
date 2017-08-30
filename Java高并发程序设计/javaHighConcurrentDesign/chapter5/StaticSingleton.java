package javaHighConcurrentDesign.chapter5;

public class StaticSingleton {

    public static int i = 1;

    private StaticSingleton() {
        System.out.println("StaticSingleton is create" + this.toString());
    }

    private static class SingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }

    public static void main(String[] args) {
        Runnable r = () -> {
            //System.out.println(StaticSingleton.i);
            StaticSingleton singleton = StaticSingleton.getInstance();
            System.out.println(singleton.toString());
        };
        Thread thread = new Thread(r);
        thread.start();
    }

}
