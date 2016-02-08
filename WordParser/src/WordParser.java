import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains several methods for parsing text into words. Assumes words are
 * separated by whitespace.
 */
public class WordParser {

	/**
	 * Converts text into a consistent format by converting text to lower-
	 * case, replacing non-word characters and underscores with a single
	 * space, and finally removing leading and trailing whitespace. (See
	 * the {@link String} class for several helpful methods.)
	 *
	 * @param text - original text
	 * @return text without special characters and leading or trailing spaces
	 */
	public static String cleanText(String text) {
		text = text.toLowerCase();
		text = text.replaceAll("[\\W_]+", " ");
		text = text.trim();
		return text;
	}

	/**
	 * Splits text into words by whitespaces, cleans the resulting words
	 * using {@link #cleanText(String)} so that they are in a consistent
	 * format, and adds non-empty words to an {@link ArrayList}.
	 *
	 * <p><em>
	 * You must use the {@link #cleanText(String)} method and an enhanced
	 * for loop to receive full credit for this method.
	 * </em></p>
	 *
	 * @param text - original text
	 * @return list of cleaned words
	 */
	public static List<String> parseText(String text) {
		ArrayList<String> words = new ArrayList<>();
		text = cleanText(text);
		String[] word = text.split(" ");
		for (String elem : word) {
			if (elem.length() != 0) {
				words.add(elem);
			}
		}
		return words;
	}

	/**
	 * Reads a file line-by-line and parses the resulting line into words
	 * using the {@link #parseText(String)} method. Adds the parsed words
	 * to a master list of words, which is returned at the end.
	 *
	 * <p><em>
	 * You must use the {@link #parseText(String)}, a try-with-resources
	 * block, and read and store only one line at a time to receive full
	 * credit for this method. You can throw an IOException in this method. 
	 * </em></p>
	 *
	 * @param path - file path to open
	 * @return list of cleaned words
	 * @throws IOException
	 */
	public static List<String> parseFile(Path path) throws IOException {
		ArrayList<String> words = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(path)) {
			String line = null;
		List<String> l = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			l = parseText(line);
			words.addAll(l);
		}
		return words;
		}
	}
}
