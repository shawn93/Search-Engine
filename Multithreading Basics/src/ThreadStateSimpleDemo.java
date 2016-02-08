import java.util.Arrays;

/**
 * Demonstrates simple changes in thread state for a main thread and a
 * single worker thread.
 */
public class ThreadStateSimpleDemo {

    private Thread parentThread;
    private Thread workerThread;

    /**
     * Gets the current main thread, spawns a new worker thread, and
     * outputs the changes in thread state.
     *
     * @throws InterruptedException - if a thread is interrupted
     */
    public ThreadStateSimpleDemo() throws InterruptedException {
        parentThread = Thread.currentThread();
        workerThread = new Worker();

        output(1);

        workerThread.start();
        calculate(500);

        workerThread.join();
        output(4);
    }

    /**
     * Performs some meaningless computation. Used to keep threads busy
     * without calling sleep() or wait() explicitly.
     *
     * @param size - size of array to initialize and sort
     */
    private static void calculate(int size) {
        double[] junk = new double[size];

        for(int i = 0; i < junk.length; i++) {
            junk[i] = Math.random();
        }

        Arrays.sort(junk);
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
     * Worker thread that performs some meaningless computation, that
     * should take longer than the main thread.
     */
    private class Worker extends Thread {
        @Override
        public void run() {
            output(2);
            calculate(1000);
            output(3);
        }
    }

    /**
     * Starts the simple thread state demo.
     *
     * @param args - unused
     * @throws InterruptedException - if a thread is interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        new ThreadStateSimpleDemo();
    }
}
