import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * This class demonstrates different types of collections. In particular, it
 * illustrates the difference between a different types of lists and sets.
 */
public class CollectionDemo {

	public static void parseLine(String line) {

		/*
		 * Note how we must specify the element type using the Generics < >
		 * notation, and with maps we must specify the type of both the key
		 * and the value elements.
		 *
		 * Also notice we cannot use primitive types. Instead of an int, we
		 * must store its object-equivalent Integer.
		 */

		ArrayList<String> wordList = new ArrayList<String>();

		HashSet<String> wordHashSet = new HashSet<String>();
		TreeSet<String> wordTreeSet = new TreeSet<String>();

		/*
		 * We will use the String.split() method to split a line into
		 * individual words. You can use different regular expressions to
		 * decide how to separate words. Try switching which line is
		 * commented to see the difference.
		 */

//		String[] words = line.split("\\s");		// Split by spaces
		String[] words = line.split("\\W");		// Split by non-word characters

		/*
		 * To print the contents of an array, use the helper method in
		 * the Arrays class. Otherwise, you will not get the expected output.
		 */
		System.out.println(Arrays.toString(words));

		/*
		 * This shows a for-each or enhanced-for loop. Whenever possible,
		 * use these types of loops instead of a traditional for loop. It
		 * uses iterators, which can often be more efficient than accessing
		 * an element by index or key.
		 */
		for (String word : words) {

			// Convert word into default format
			word = word.trim();			// Remove leading or trailing spaces
			word = word.toLowerCase();	// Convert to lowercase

			// Remove words that were just spaces
			if (word.isEmpty()) {
				continue;
			}

			// Add word to various data structures
			wordList.add(word);
			wordHashSet.add(word);
			wordTreeSet.add(word);
		}

		// Print out results in a consistent format
		String format = "%-10s : %02d items : %s\n";
		System.out.printf("\n");
		System.out.printf(format, "ArrayList", wordList.size(), wordList);
		System.out.printf(format, "HashSet",   wordHashSet.size(), wordHashSet);
		System.out.printf(format, "TreeSet",   wordTreeSet.size(), wordTreeSet);

		System.out.println();
	}

	public static void main(String[] args) {

		String test1 = "egg dill banana carrot banana apple";
		parseLine(test1);

		String test2 = "Hello, hello! How are you today? It is a bright-bright morning.";
		parseLine(test2);
	}

}
