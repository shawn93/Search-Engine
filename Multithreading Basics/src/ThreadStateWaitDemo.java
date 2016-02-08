
/**
 * Demonstrates simple changes in thread state for a main thread and a
 * single worker thread. Focuses on the waiting and timed waiting states.
 */
public class ThreadStateWaitDemo {

    private final Object lock;

    private final Thread parentThread;
    private final Thread workerThread;

    /**
     * Gets the current main thread, spawns a new worker thread, and
     * outputs the changes in thread state. Will sleep a small amount to
     * give the worker thread a chance to run before the second output.
     *
     * @throws InterruptedException - if a thread is interrupted
     */
    public ThreadStateWaitDemo() throws InterruptedException {
        lock = new Object();

        parentThread = Thread.currentThread();
        workerThread = new Worker();

        output(1);

        // get worker running (and time to enter wait state)
        workerThread.start();
        Thread.sleep(500);
        output(2);

        // wake up sleeping thread early
        // use the same object for notify() that wait() used!
        synchronized (lock) {
            lock.notifyAll();
        }

        // wait for worker to wake up and exit
        workerThread.join();
        output(4);
    }

    /**
     * Outputs the state of the main (parent) thread and worker thread.
     *
     * @param id - id to distinguish output
     */
    private void output(int id) {
        System.out.printf("%d) %s is %-10s %s is %s%n", id,
                parentThread.getName(), parentThread.getState(),
                workerThread.getName(), workerThread.getState());
    }

    /**
     * Worker thread that waits to be notified or 5 seconds (whichever
     * happens first).
     */
    private class Worker extends Thread {
        @Override
        public void run() {
            // wait must be called on the synchronized object
            synchronized (lock) {
                try {
                    lock.wait(5000);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                output(3);
            }
        }
    }

    /**
     * Starts the simple thread state demo to show waiting and timed
     * waiting states.
     *
     * @param args - unused
     * @throws InterruptedException - if a thread is interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        new ThreadStateWaitDemo();
    }
}
