import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * The SearchBuilder is to parse the search query and search them from the index 
 * then write the search results to a text file. 
 * 
 */ 
public class ThreadSafeSearchBuilder {

    private final LinkedHashMap<String, ArrayList<SearchResult>> searchresult;
    private final MultiReadWriteLock searchLock;
    private final ThreadSafeInvertedIndex index;
    private final WorkQueue workers;
    private int pending;
    
    /**
     * Initialize search result
     */
    public ThreadSafeSearchBuilder(ThreadSafeInvertedIndex index, WorkQueue workers) {
        searchresult = new LinkedHashMap<String, ArrayList<SearchResult>>();
        searchLock = new MultiReadWriteLock();
        pending = 0;
        this.workers = workers;
        this.index = index;
    }

    /**
     * Write the search result in a file.
     * 
     * @param path - path to write the result
     * @throws IOException
     */
    public void writeResult(String path) throws IOException {
        
        searchLock.lockRead();
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String query: searchresult.keySet()) {
                bw.write(query);
                bw.newLine();
                
                for (SearchResult result : searchresult.get(query)) { 
                    bw.write(result.toString());
                    bw.newLine();
                }
                
                bw.newLine();
            }
        }
        
        searchLock.unlockRead();
        
    }
    
    /**
     * Search each query word from the query file and then add the word, path, frequency
     * and first position in the path to search result.
     * 
     * @param path - a path of query file
     * @param index - an inverted index to search the query
     * @throws IOException
     */ 
    public void parseQuery(String path) throws IOException {
        searchLock.lockWrite();
        try (BufferedReader bufferedreader = Files.newBufferedReader(Paths.get(path))) {
            String line = null;  
            
            while ((line = bufferedreader.readLine()) != null) {
                searchresult.put(line, null);
                workers.execute(new SearchMinion(line));
            }
        }
        searchLock.unlockWrite();
    }
    
    private class SearchMinion implements Runnable {

        String query;

        public SearchMinion(String query) {
            Driver.logger.debug("SearchMinion created for {}", query);
            this.query = query;
            incrementPending(); 
        }

        @Override
        public void run() {

            ArrayList<SearchResult> result = index.search(WordParser.parseText(query));
            searchLock.lockWrite();
            searchresult.put(query, result);
            searchLock.unlockWrite();
            decrementPending();
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
