/**
 * A SearchResult object stored path, frequency and position.
 */
public class SearchResult implements Comparable<SearchResult> {

    private final String path;
    private int frequency;
    private int position;
    
    /**
     * Initialize path, frequency, position.
     * 
     * @param path - file path
     * @param frequency - the frequency of search query 
     * @param position - the first position of the search query
     */
    public SearchResult(String path, int frequency, int position) {
        this.path = path;
        this.frequency = frequency;
        this.position = position;
    }
    
    /**
     * Update the frequency and update the position if less than.
     * 
     * @param frequency - the frequency of search query
     * @param position - the first position of the search query
     */
    public void update(int frequency, int position) {
        this.frequency += frequency;
        
        if (position < this.position) {
            this.position = position;
        }
    }
    
    @Override
    public String toString() {
        return "\"" + path + "\"" + ", " + frequency + ", " + position;
    }

    @Override
    public int compareTo(SearchResult other) {
        
        if (Integer.compare(frequency, other.frequency) != 0) {
            return Integer.compare(other.frequency, frequency);
        }
        else if (Integer.compare(position, other.position) != 0) {
            return Integer.compare(position, other.position);
        }

        return String.CASE_INSENSITIVE_ORDER.compare(path, other.path);
    }
    
	/**
	 * Get method for the path of a Search Result
	 * @return 
	 */
	public String getPath(){
		return this.path;
	}
}
