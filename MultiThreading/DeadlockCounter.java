package MultiThreading;

public class DeadlockCounter {
    private int counter = 0;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    void increment() throws InterruptedException {
        synchronized (lock1) {
            System.out.println("Incrementing counter acquires lock1");
            synchronized (lock2) {
                System.out.println("Incrementing counter acquires lock2");
                counter++;
            }
        }
    }

    void decrement() throws InterruptedException {
        synchronized (lock2) {
            System.out.println("Decrementing counter acquires lock2");
            synchronized (lock1) {
                System.out.println("Decrementing counter acquires lock1");
                counter--;
            }
        }
    }

    Runnable incrementThread = () -> {
        try {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        } catch (InterruptedException e) {

        }
    };

    Runnable decrementThread = () -> {
        try {
            for (int i = 0; i < 100; i++) {
                decrement();
            }
        } catch (InterruptedException e) {

        }
    };

    public void runTest() throws InterruptedException {
        Thread thread1 = new Thread(incrementThread);
        Thread thread2 = new Thread(decrementThread);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println(counter);
    }

    public static void main(String[] args) {
        DeadlockCounter counter = new DeadlockCounter();
        try {
            counter.runTest();
        } catch (InterruptedException e) {

        }
    }
}
