import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordIndex {
    /**
     * Stores a mapping of words to the positions those words were found.
     */
    private TreeMap<String, TreeSet<Integer>> index;
    
    /**
     * Properly initializes the index. 
     */
    public WordIndex() {
        index = new TreeMap<String, TreeSet<Integer>>();    
    }

    /**
     * Properly adds a word and position to the index. Must initialize inner
     * data structure if necessary. Make sure you consider how to handle
     * duplicates (duplicate words, and words with duplicate positions).
     *
     * @param word
     *            - word to add to index
     * @param position
     *            - position word was found
     * @return true if this was a unique entry,
     *         false if no changes were made to the index
     */
    public boolean add(String word, int position) {
        TreeSet<Integer> s = new TreeSet<Integer>();
        s.add(position);
        if (index.containsKey(word) == false) {
        	index.put(word, s);
            return true;
        }
        if (index.containsKey(word)) {
        	return index.get(word).add(position);   
        }
        return false;
    }

    /**
     * Returns the number of times a word was found (i.e. the number of
     * positions associated with a word in the index).
     *
     * @param word - word to look for
     * @return number of times word was found
     */
    public int count(String word) {
        if (index.containsKey(word)) {
        	return index.get(word).size();
        }
        return 0;
    }

    /**
     * Returns the total number of words stored in the index.
     * @return number of words
     */
    public int words() {
    	return index.size();
    }

    /**
     * Tests whether the index contains the specified word.
     * @param word - word to look for
     * @return true if the word is stored in the index
     */
    public boolean contains(String word) {
    	return index.containsKey(word);
    }

    /**
     * Safely returns the set of positions for a specified word (or an
     * empty set if the word is not found). Be wary of directly returning
     * a reference to your private mutable data!
     *
     * @param word - word to look for
     * @return set of positions associated with word (will be empty if
     *         word was not found)
     */
    public Set<Integer> positions(String word) {
        if (index.containsKey(word)) {
        	return Collections.unmodifiableSet(index.get(word));
        }
        return Collections.emptySet();
    }

    /**
     * Returns a string representation of this index for debugging.
     */
    @Override
    public String toString() {
        // THIS METHOD IS PROVIDED FOR YOU
        return index.toString();
    }
}
