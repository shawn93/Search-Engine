import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Driver {

    public static final Logger logger = LogManager.getLogger();
    private static int PORT;
    public static ThreadSafeInvertedIndex invertedindex;
    public static WorkQueue workers;
    
    /**
     * Recursively processes all text files in a directory and output the result that
     * store the mapping from words to the documents (and position within those documents)
     * where those words were found.
     * Search the query from queries and write the search result (word, path, frequency
     * and first position in the path) to a text file.
     * Search inverted index using multiple threads.
     * 
     * @param args - arguments to parse
     * @param input - the input path
     * @param output - the output path
     * @param query - the path to a text file of queries
     * @param resultpath - the output of search results output file
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        
        ArgumentParser parser = new ArgumentParser(args);
        
        if (parser.hasFlag("-t") == false || parser.getValue("-t") == null) {
            InvertedIndex invertedindex = new InvertedIndex();
            
            if (parser.hasValue("-d")) {
                String input = parser.getValue("-d");
                if (Files.exists(Paths.get(input))) {
                    InvertedIndexBuilder.parseDirectory(input, invertedindex);
                   
                    if (parser.getValue("-i") != null) {
                        String output = parser.getValue("-i");      
                            invertedindex.write(output);
                    } 
                    else if (parser.hasFlag("-i")) {
                        invertedindex.write("index.txt");
                    }
                }
            }
            
            if (parser.getValue("-q") != null) {
                String query = parser.getValue("-q");
                if (Files.exists(Paths.get(query))) {
                    SearchBuilder searchbuilder = new SearchBuilder();
                    searchbuilder.parseQuery(query, invertedindex);

                    if (parser.getValue("-s") != null) {
                        String resultpath = parser.getValue("-s");
                        if (resultpath.charAt(0) == 'r') {
                            searchbuilder.writeResult(resultpath);
                        }
                    } 
                    else if (parser.hasFlag("-s")) {
                        searchbuilder.writeResult("search.txt");
                    }
                }
            }
        }
        else {
            invertedindex = new ThreadSafeInvertedIndex();
           
            Integer minions; 
            
            if (parser.hasFlag("-t") && parser.getValue("-t") != null){
                try {
                    minions = Integer.parseInt(parser.getValue("-t"));
                    if (minions > 0){
                        workers = new WorkQueue(minions);
                    }
                    else {
                        workers = new WorkQueue();
                    }
                            
                } catch(NumberFormatException e){
                    workers = new WorkQueue();
                }
                
            }
            else {
                workers = new WorkQueue();
            }
            
            ThreadSafeInvertedIndexBuilder indexbuilder = new ThreadSafeInvertedIndexBuilder(invertedindex, workers);
            WebCrawler crawler = new WebCrawler(invertedindex, workers);
            ThreadSafeSearchBuilder searchbuilder = new ThreadSafeSearchBuilder(invertedindex, workers);
            
            
            if (parser.hasFlag("-u") && parser.getValue("-u") != null) {
                crawler.start(parser.getValue("-u"));
                crawler.finish();
            }
            
            if (parser.hasValue("-d")) {
                String input = parser.getValue("-d");
                if (Files.exists(Paths.get(input))) {
                    
                    indexbuilder.parseDirectory(input, invertedindex);
                    indexbuilder.finish();
                }
            }
            
            if (parser.hasFlag("-i")) {
                if (parser.getValue("-i") != null){
                    String output = parser.getValue("-i");      
                    invertedindex.write(output);
                }
                else{
                    invertedindex.write("index.txt");
                }
            }
            
            if (parser.getValue("-q") != null) {
                String query = parser.getValue("-q");
                
                if (Files.exists(Paths.get(query))) {                 
                    searchbuilder.parseQuery(query);
                    searchbuilder.finish();
                }
            }
            
            if (parser.hasFlag("-s")) {
                if (parser.getValue("-s") != null) {
                    String resultpath = parser.getValue("-s");
                    
                    if (resultpath.charAt(0) == 'r') {
                        searchbuilder.writeResult(resultpath);
                    }
                    else {
                        searchbuilder.writeResult("search.txt");
                    }                 
                }
                searchbuilder.finish();
            }
            
            if (parser.hasFlag("-p")){
                PORT = Integer.parseInt(parser.getValue("-p"));
            }
            else{
                PORT = 8080;
            }

            Server server = new Server(PORT);

            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);
            
            handler.addServletWithMapping(LoginUserServlet.class, "/login");
            handler.addServletWithMapping(LoginRegisterServlet.class, "/register");
            handler.addServletWithMapping(HistoryServlet.class, "/history");
            handler.addServletWithMapping(SearchServlet.class, "/");
            handler.addServletWithMapping(ResultServlet.class, "/results");
            handler.addServletWithMapping(ChangePasswordServlet.class, "/changepassword");
            
            try {
                server.start();
                server.join();
            } catch (Exception e) {
                System.out.println("Server Exception.");
                System.exit(-1);
            }

            workers.shutdown();
        }
    }
}
