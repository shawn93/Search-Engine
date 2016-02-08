import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

    private static final int PORT = 80;

    private final ThreadSafeInvertedIndex index;
    private final WorkQueue workers;
    private int pending;
    private final HashSet<String> links;
    private final MultiReadWriteLock linkLock;

    public WebCrawler(ThreadSafeInvertedIndex index, WorkQueue workers) {
        this.index = index;
        this.workers = workers;
        pending = 0;
        links = new HashSet<String>();
        linkLock = new MultiReadWriteLock();
    }

    /**
     * Fetches the web page at the provided URL by opening a socket, sending an
     * HTTP request, removing the headers, and returning the resulting HTML
     * code.
     * 
     * @param url - web page to download
     * @return html code
     */
    private String fetchText(String url) { 
        ArrayList<String> local = new ArrayList<String>();
        StringBuffer htmlBuffer = new StringBuffer();
        boolean head = true;

        URL link = null;
        URL base = null;

        try {
            link = new URL(url);
            
            base = new URL(url);
        } catch (MalformedURLException e) {
            Driver.logger.debug("URL broke url: {} base {}", link, base);
            return url;
        }

        try ( Socket socket = new Socket(link.getHost(), PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream())

        ) {
            // craftRequest();
            String host = link.getHost();
            String resource = link.getFile().isEmpty() ? "/" : link.getFile();

            StringBuffer output = new StringBuffer();
            output.append("GET " + resource + " HTTP/1.1\n");
            output.append("Host: " + host + "\n");
            output.append("Connection: close\n");
            output.append("\r\n");
            // end craftRequest();

            String request = output.toString();
            writer.println(request);
            writer.flush();
            String line = reader.readLine();

            while (line != null) {
                if (head) {
                    if (line.trim().isEmpty()) {
                        head = false;
                    }
                } 
                else {
                    htmlBuffer.append(line + "\n");
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            Driver.logger.debug("Could not open Socket for {}", url);
            return url;
        }

        local = listLinks(htmlBuffer.toString(), base);
        linkLock.lockWrite();
        for (String s : local) {
            if (!links.contains(s)) {
                links.add(s);

                if (links.size() <= 50) {
                    workers.execute(new WebMinion(s));
                }

            }
        }
        linkLock.unlockWrite();

        String html;
        html = HTMLCleaner.cleanHTML(htmlBuffer.toString());
        return html;
    }

    public void start(String url) {
        linkLock.lockWrite();
        links.add(url);
        linkLock.unlockWrite();
        workers.execute(new WebMinion(url));
    }

    private class WebMinion implements Runnable {

        private String link;
        private ThreadSafeInvertedIndex local;

        public WebMinion(String link) {
            local = new ThreadSafeInvertedIndex();
            this.link = link;
            incrementPending();
        }

        @Override
        public void run() {
            String html = fetchText(this.link);
            ArrayList<String> words = parseWords(html);
            for (int i = 0; i < words.size(); i++) {
                local.add(words.get(i), this.link, i+1);
            }

            index.addAll(local);
            decrementPending();
        }
    }

    /**
     * Indicates that we now have additional "pending" work to wait for. We need
     * this since we can no longer call join() on the threads. (The threads keep
     * running forever in the background.)
     * 
     * We made this a synchronized method in the outer class, since locking on
     * the "this" object within an inner class does not work.
     */
    private synchronized void incrementPending() {
        pending++;
        Driver.logger.debug("Pending increased to {}", getPending());
    }

    /**
     * Indicates that we now have one less "pending" work, and will notify any
     * waiting threads if we no longer have any more pending work left.
     */
    private synchronized void decrementPending() {
        pending--;
        Driver.logger.debug("Pending decreased to {}", getPending());

        if (pending <= 0) {
            notifyAll();
        }
    }

    /**
     * Get method for the value of pending
     * 
     * @return pending
     */
    public synchronized int getPending() {
        return pending;
    }

    /**
     * Helper method, that helps a thread wait until all of the current work is
     * done. This is useful for resetting the counters or shutting down the work
     * queue.
     */
    public synchronized void finish() {
        try {
            while (getPending() > 0) {
                Driver.logger.debug("Waiting until finished");
                this.wait();
            }
        } catch (InterruptedException e) {
            Driver.logger.debug("Finish interrupted", e);
        }
    }



    /**
     * Parses the provided plain text (already cleaned of HTML tags) into
     * individual words.
     *
     * THIS METHOD IS PROVIDED FOR YOU. DO NOT MODIFY.
     *
     * @param text - plain text without html tags
     * @return list of parsed words
     */
    public static ArrayList<String> parseWords(String text) {
        ArrayList<String> words = new ArrayList<String>();
        text = text.replaceAll("[\\W_]+", " ").toLowerCase();

        for (String word : text.split("\\s+")) {
            word = word.trim();

            if (!word.isEmpty()) {
                words.add(word);
            }
        }

        return words;
    }

    /**
     * Parses the provided text for HTML links.
     * 
     * @param text - valid HTML code, with quoted attributes and URL encoded links
     * @return list of links found in HTML code
     */
    public ArrayList<String> listLinks(String text, URL base) { 
        String REGEX = "(?i)(?s)<a.*?href[\\s]*=[\\s]*[\"](.+?\\..+?)\"+";
        int GROUP = 1;
        ArrayList<String> links = new ArrayList<String>();
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(text);
        
        while (m.find()) {
            try {
                URL absolute = new URL(base, m.group(GROUP));
                String a = absolute.toURI().normalize().toString();
                links.add(a);
            } catch (MalformedURLException | URISyntaxException e) {
                Driver.logger.debug("Bad URL {}");
            }
        }
        return links;
    }
}