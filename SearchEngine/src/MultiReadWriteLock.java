/**
 * A simple custom lock that allows simultaneously read operations, but
 * disallows simultaneously write and read/write operations.
 * 
 * You do not need to implement any form or priority to read or write
 * operations. The first thread that acquires the appropriate lock should be
 * allowed to continue.
 * 
 */

public class MultiReadWriteLock {
    private int readers;
    private int writers;

    /**
     * Initializes a multi-reader single-writer lock.
     */
    public MultiReadWriteLock() {
        readers = 0;
        writers = 0;
    }

    /**
     * Will wait until there are no active writers in the system, and then will
     * increase the number of active readers.
     */
    public synchronized void lockRead() {
        while (writers > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.err.println("Warning: exception when lock read.");
            }
        }
        //Driver.logger.debug("Locked Read");
        readers++;
    }

    /**
     * Will decrease the number of active readers, and notify any waiting
     * threads if necessary.
     */
    public synchronized void unlockRead() {
        readers--;
        if (readers <= 0){
            this.notifyAll(); 
        }
        //Driver.logger.debug("Unlocked Read");
    }

    /**
     * Will wait until there are no active readers or writers in the system, and
     * then will increase the number of active writers.
     */
    public synchronized void lockWrite() {

        while (readers > 0 || writers > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.err.println("Warning: exception when lock write.");
            }
        }
        //Driver.logger.debug("Locked Write");
        writers++;

    }

    /**
     * Will decrease the number of active writers, and notify any waiting
     * threads if necessary.
     */
    public synchronized void unlockWrite() {
        writers--;
        //Driver.logger.debug("Unlocked Write");
        this.notifyAll();
    }
}