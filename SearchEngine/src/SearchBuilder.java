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
public class SearchBuilder {
    
    private final LinkedHashMap<String, ArrayList<SearchResult>> searchresult;

    /**
     * Initialize search result
     */
    public SearchBuilder() {
        searchresult = new LinkedHashMap<String, ArrayList<SearchResult>>();
    }

    /**
     * Write the search result in a file.
     * 
     * @param path - path to write the result
     * @throws IOException
     */
    public void writeResult(String path) throws IOException {
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
    }
    
    /**
     * Search each query word from the query file and then add the word, path, frequency
     * and first position in the path to search result.
     * 
     * @param path - a path of query file
     * @param index - an inverted index to search the query
     * @throws IOException
     */ 
    public void parseQuery(String path, InvertedIndex index) throws IOException {
        try (BufferedReader bufferedreader = Files.newBufferedReader(Paths.get(path))) {
            String line = null;  
            
            while ((line = bufferedreader.readLine()) != null) {
                ArrayList<SearchResult> result = index.search(WordParser.parseText(line));
                searchresult.put(line, result);
            }
        } 
    }
}
