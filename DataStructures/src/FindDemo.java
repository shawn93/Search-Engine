import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * This class demonstrates how to find all elements that start with a specific
 * letter using a set or a list.
 */
public class FindDemo {

	/** An array of animals used for demonstration purposes. */
	private static final String[] animals = new String[] {"ant", "ape", "bat",
			"bee", "boa", "cat", "cow", "dog"};

	/** An array of letters that we want to find matches for. */
	private static final String[] letters = new String[] {"a", "b", "e"};

	/**
	 * This example demonstrates how to efficiently find all the words in
	 * a sorted set that start with the specified letter. For example, how
	 * to find all of the words in animals that starts with the letter "a".
	 */
	private static void setDemo() {
		/*
		 * We need a sorted set for this example. We use Collections.addAll()
		 * to add all of the animals to a set. This is more efficient than
		 * adding elements one at a time.
		 */
		TreeSet<String> set = new TreeSet<String>();
		Collections.addAll(set, animals);

		// Uses an enhanced for loop to iterate through letters
		for (String letter : letters) {

			System.out.print(letter.toUpperCase() + ": ");

			/*
			 * We will use the tailSet() method to generate a reference to our
			 * set that starts in the correct place and then uses an enhanced
			 * for loop to continue iteration.
			 *
			 * You can also try using ceiling() and higher() in a traditional
			 * for loop instead.
			 */
			for (String animal : set.tailSet(letter)) {

				/*
				 * Just because we started in the right place, does not mean
				 * we will end in the right place. Once we no longer have a
				 * word that starts with our letter, we can quit. For example,
				 * if we are looking for words that start with "a" and we
				 * encounter the word "bat", we know that we have seen all of
				 * the words that start with "a" because the set is sorted.
				 */
				if (!animal.startsWith(letter)) {
					break;
				}

				System.out.print(animal + " ");
			}

			System.out.println();
		}

		System.out.println();
	}

	/**
	 * This example demonstrates how to deal with an unsorted list. First,
	 * we sort the list and then use binary search to find the words.
	 */
	private static void listDemo() {

		/*
		 * Again, make sure to use the helper methods in Collections. There
		 * is an efficient sort implementation already created for you!
		 */
		ArrayList<String> list = new ArrayList<String>();
		Collections.addAll(list, animals);
		Collections.sort(list);

		for (String letter : letters) {

			System.out.print(letter.toUpperCase() + ": ");

			/*
			 * Binary search is an efficient search implementation for a
			 * sorted list. If the element is found, index will be the first
			 * location of the element in the list.
			 */
			int index = Collections.binarySearch(list, letter);

			/*
			 * What happens if the element is not found? In our case, we
			 * are looking for letters that do not exist in our list.
			 *
			 * The binary search method will return a negative value to
			 * indicate the element was not found. However, this negative
			 * value is informative. It tells us where this element would
			 * exist if it were in the list. For example, if we wanted to
			 * insert the letter "a" into our sorted list, it should be at
			 * the front.
			 *
			 * This "insertion point" will tell us where to start. We do
			 * have to do some conversion since the value is negative, which
			 * is explained in the API.
			 */
			if (index < 0) {
				index = -(index + 1);
			}

			// For simplicity, lets use a traditional for loop. However,
			// you can make this more efficient using an iterator!
			for (int i = index; i < list.size(); i++) {

				// Again, make sure to stop in the appropriate location
				if (!list.get(i).startsWith(letter)) {
					break;
				}

				System.out.print(list.get(i) + " ");
			}

			System.out.println();
		}

		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println("TreeSet Example:");
		setDemo();

		System.out.println("ArrayList Example:");
		listDemo();
	}
}
