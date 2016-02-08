import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The Invertedindexbuilder is to parse every text file in a directory and parse
 * each text file then store the word, path and posiitons of this text file to a 
 * data structure
 * 
 */ 
public class ThreadSafeInvertedIndexBuilder {

    private final ThreadSafeInvertedIndex index;
    private final WorkQueue workers;
    private int pending;
    
    public ThreadSafeInvertedIndexBuilder(ThreadSafeInvertedIndex index, WorkQueue workers) {
        this.index = index;
        this.workers = workers;
        pending = 0;
    }

    /**
     * Traverse the directory by using directory traverser and then parse every
     * file to add the word, path, positions to an inverted index.
     *
     *@param directory - directory to be parsed
     *@param index - a thread safe inverted index
     * @throws IOException
     */ 
    public synchronized void parseDirectory(String directory, ThreadSafeInvertedIndex index) throws IOException {
        for (Path fpath : DirectoryTraverser.traverse(Paths.get(directory))) {
            workers.execute(new IndexMinion(fpath));
        }
    }
   
    private class IndexMinion implements Runnable {

        private final Path directory;

        public IndexMinion(Path directory) {
            Driver.logger.debug("IndexMinion created for {}", directory);
            this.directory = directory;
            incrementPending();
        }
        
        @Override
        public void run() {
            if (!Files.isDirectory(directory)) {
                try {

                    InvertedIndex local = new InvertedIndex();
                    InvertedIndexBuilder.parseFile(directory, local);
                    index.addAll(local);
                    
                } catch (IOException e) {
                    System.out.println("Unable to Read directory " + directory);
                }
                decrementPending(); 
            }
        }
    }
    
    /**
     * Indicates that we now have additional "pending" work to wait for. We
     * need this since we can no longer call join() on the threads. (The
     * threads keep running forever in the background.)
     *
     * We made this a synchronized method in the outer class, since locking
     * on the "this" object within an inner class does not work.
     */
    private synchronized void incrementPending() {
        pending++;
        Driver.logger.debug("Pending is now {}", pending);
    }

    /**
     * Indicates that we now have one less "pending" work, and will notify
     * any waiting threads if we no longer have any more pending work left.
     */
    private synchronized void decrementPending() {
        pending--;
        Driver.logger.debug("Pending is now {}", pending);

        if (pending <= 0) {
            this.notifyAll();
        }
    }
    
    /**
     * Helper method, that helps a thread wait until all of the current
     * work is done. This is useful for resetting the counters or shutting
     * down the work queue.
     */
    public synchronized void finish() {
        try {
            while (pending > 0) {
                Driver.logger.debug("Waiting until finished");
                this.wait();
            }
        }
        catch (InterruptedException e) {
            Driver.logger.debug("Finish interrupted", e);
        }
    }
}

