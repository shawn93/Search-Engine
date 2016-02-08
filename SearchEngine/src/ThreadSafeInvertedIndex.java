import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThreadSafeInvertedIndex extends InvertedIndex{

    private MultiReadWriteLock indexLock;
    
    public ThreadSafeInvertedIndex () {
        indexLock = new MultiReadWriteLock();
    }
    
    @Override
    public void add(String word, String path, Integer position) {
        indexLock.lockWrite();
        super.add(word, path, position);
        indexLock.unlockWrite();
    }
    
    @Override
    public void write(String path) throws IOException {
        indexLock.lockRead();
        super.write(path);
        indexLock.unlockRead();
    }
    
    @Override
    public ArrayList<SearchResult> search(List<String> queries) {
        indexLock.lockRead();
        ArrayList<SearchResult> results = super.search(queries);
        indexLock.unlockRead();
        return results;
    }
    
    @Override
    public void addAll(InvertedIndex other) {
        indexLock.lockWrite();
        super.addAll(other);
        indexLock.unlockWrite();
    }
}
