package javaHighConcurrentDesign.customeTest;

/**
 * 前自增与后自增都是线程不安全
 */
public class CountTest {

    static class TestThread implements Runnable {

        public int count = 0;

        @Override
        public void run() {
            for (int i = 0; i <100000 ; i++) {
                //synchronized (this) {
                    ++count;
                //}
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestThread testThread = new TestThread();

        Thread thread = new Thread(testThread);
        Thread thread2 = new Thread(testThread);
        thread.start();
        thread2.start();
        Thread.sleep(500);
        System.out.println(testThread.count);
    }
}
