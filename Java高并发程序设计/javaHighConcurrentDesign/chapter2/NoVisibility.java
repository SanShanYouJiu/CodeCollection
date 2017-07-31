package javaHighConcurrentDesign.chapter2;

/**
 * 在client下 由于JIT并没有做足够的优化 ReaderThread可以发现这个改动 并退出程序
 * 但是在Server模型下 由于系统优化的结果 ReaderThread无法看到主线程的修改
 * 导致ReaderThread永远无法退出  这显然不是我们想看到结果 这个问题就是一个典型的可见性问题
 */
public class NoVisibility {

    private static  boolean ready;
    //private static  volatile boolean ready;
    private static  int number;

    private static class ReaderThread extends Thread {

        @Override
        public void run() {
            while (!ready) {
                System.out.println(number);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number=42;
        ready=true;
        Thread.sleep(10000);
    }


}
