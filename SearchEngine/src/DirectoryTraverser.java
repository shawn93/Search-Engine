import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Recursively processes all text files in a directory and store these text files
 * in an list. 
 */
public class DirectoryTraverser {

    /**
     * Recursively processes all text files in a directory and store these text 
     * files in a list
     * 
     * @param files - an arraylist to store text files in a directory
     * @param file - paths in a directory
     * @throws IOException
     */
    public static ArrayList<Path> traverse(Path directory) throws IOException {
        ArrayList<Path> files = new ArrayList<>();
        
        try (DirectoryStream<Path> listing = Files.newDirectoryStream(directory)) {
            for (Path file : listing) {
                if (file.toString().toLowerCase().endsWith("txt")) {
                    files.add(file);
                }
                
                if (Files.isDirectory(file)) {          
                    files.addAll(traverse(file));
                }
            }
        }
        
        return files;
    }
}
