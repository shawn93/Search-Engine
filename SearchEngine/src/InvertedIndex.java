import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * An invertedindex used to store a word,it's path and positions in the text file 
 * to an invertedindex. Write in a text file of the specific format and Search query
 * word from the index. 
 * 
 */
public class InvertedIndex {
    private final TreeMap<String, TreeMap<String, TreeSet<Integer>>> index;
    
    /**
     * Initialize an inverted index
     */
    public InvertedIndex() {
        index = new TreeMap<String, TreeMap<String, TreeSet<Integer>>>();
    }
    
    /**
     * Adding the word, path, position to the inverted index
     * 
     * @param info - path of a word and it's positions in this path (text file)
     * @param pos -  position of each word
     * @throws IOException
     */
    public void add(String word, String path, Integer position) {
        if (!index.containsKey(word)) {
            index.put(word, new TreeMap<>());
        }
        
        if (!index.get(word).containsKey(path)) {
            index.get(word).put(path, new TreeSet<>());
        }
        
        index.get(word).get(path).add(position);

    }

    /**
     * Writes the inverted index to file.  
     *
     * @throws IOException
     */
    public void write(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            
            for (String word: index.keySet()) {
                bw.write(word);
                bw.newLine();
                for (String wordpath : index.get(word).keySet()) { 
                    bw.write("\"" + wordpath + "\"");
                    for(int i : index.get(word).get(wordpath)) {
                        bw.write(", " + i);
                    }
                    bw.newLine();
                }   
                bw.newLine();
            }
        } 
    }
    
    @Override
    public String toString() {
        return index.toString();
    }
    
    /**
     * Search words from the index.
     * 
     * @param searchwords - words need to be searched
     * @return
     */
    public ArrayList<SearchResult> search(List<String> searchwords) {
        ArrayList<SearchResult> results = new ArrayList<>();
        Map<String, SearchResult> resultMap = new HashMap<String, SearchResult>();

        for (String s : searchwords) {
            for (String word : index.tailMap(s).keySet()) { 
                
                if (!word.startsWith(s)) {
                    break;
                }
                
                for (String path : index.get(word).keySet()) {
                    if (resultMap.containsKey(path)) {
                        resultMap.get(path).update(index.get(word).get(path).size(), index.get(word).get(path).first());
                    }
                    else {
                        SearchResult searchresult = new SearchResult(path, index.get(word).get(path).size(),index.get(word).get(path).first());
                        resultMap.put(path, searchresult);
                    }
                }
            }
        }
        
        results.addAll(resultMap.values());
        Collections.sort(results);
        
        return results;
    }
    
    /**
     * Add all inverted index to another index
     * 
     * @param other - An inverted index
     */
    public void addAll(InvertedIndex other) {
        
        for (String word : other.index.keySet()) {
            if (this.index.containsKey(word) == false) {
                this.index.put(word, other.index.get(word));
            }
            else {
                for (String path : other.index.get(word).keySet()) {
                    if (this.index.get(word).containsKey(path)) {
                        this.index.get(word).get(path).addAll(other.index.get(word).get(path));
                    }
                    else {
                        this.index.get(word).put(path, other.index.get(word).get(path));
                    }
                }
            }
        }
    }
}
