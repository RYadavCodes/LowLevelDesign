package MultiThreading;

public class NonReEntrantLock {

    /*
    Any time if we acquire non-re-entrant lock twice,
    there will be deadlock as opposed to Re-entrant locks
     */

    private boolean isLocked;

    NonReEntrantLock() {
        this.isLocked = false;
    }

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            Thread.sleep(10);
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
    }

    public static void main(String[] args) {
        NonReEntrantLock lock = new NonReEntrantLock();
        try {
            lock.lock();
        } catch (InterruptedException e) {

        }
        System.out.println("Acquired lock first time");
        try {
            lock.lock();
        } catch (InterruptedException e) {

        }
        System.out.println("Acquired lock second time");
    }

}
