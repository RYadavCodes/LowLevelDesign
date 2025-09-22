package MultiThreading;

public class ReEntrantLock {

    /*
    The lock behind the synchronized methods and blocks is a reentrant.
    This means the current thread can acquire the same synchronized lock over
    and over again while holding it
     */

    private int count = 0;
    Object lock = new Object();

    void increment() {
        synchronized (lock) {
            System.out.println(("Acquired lock for first time"));
            synchronized (lock) {
                System.out.println(("Acquired lock for second time"));
                count++;
            }
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                increment();
            }
        }
    };

    public static void main(String[] args) {
        ReEntrantLock reentrantLock = new ReEntrantLock();
        try {
            Thread t1 = new Thread(reentrantLock.runnable);
            t1.start();
        } catch (Exception e) {

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        System.out.println(reentrantLock.count);
    }
}
