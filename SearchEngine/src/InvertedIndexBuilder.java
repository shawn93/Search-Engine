import java.io.BufferedReader;
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
public class InvertedIndexBuilder {

    /**
     * Parse the directory and store every word and it's position in each text file. 
     * Store the word, path, positions in files in a treemap.
     * 
     * @param words - an ArrayList to add all parsed text
     * @throws IOException
     */ 
    public static void parseFile(Path path, InvertedIndex index) throws IOException { 
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = null;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] word = WordParser.cleanText(line).split(" ");
                for (int i = 0; i< word.length; i++) {
                    if (word[i].length() != 0) {
                        counter++;
                        index.add(word[i], path.toString(), counter);
                    }
                }          
            }
        }
    }
    
    /**
     * Traverse the directory by using directory traverser and then parse every
     * file to add the word, path, positions to an inverted index.
     *
     * @throws IOException
     */ 
    public static void parseDirectory(String directory, InvertedIndex index) throws IOException {
        for (Path fpath : DirectoryTraverser.traverse(Paths.get(directory))) {
            parseFile(fpath, index);
        }
    }
}
