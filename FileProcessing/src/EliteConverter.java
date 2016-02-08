import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts a file to a simple version of 1337-speak. Demonstrates simple
 * for loops, switch statements, method overloading, the ternary operator,
 * and line-by-line file reading and writing. Does not cover exception
 * handling in depth!
 *
 * <p><em>
 * Note that this class is designed to illustrate a specific concept, and
 * may not be an example of proper class design outside of this context.
 * </em></p>
 */
public class EliteConverter {

	/**
	 * Randomly converts each letter to uppercase or lowercase. Uses a
	 * {@link StringBuilder} to build the new text.
	 *
	 * @param text - original text
	 * @return text with letters randomly converted to upper or lowercase
	 */
	public static String toRandomCase(String text) {
		char[] chars = text.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (Math.random() > 0.5) {
				chars[i] = Character.toLowerCase(chars[i]);
			}
			else {
				chars[i] = Character.toUpperCase(chars[i]);
			}
		}

		return String.valueOf(chars);
	}

	/**
	 * Converts a letter to its 1337 representation, or leaves the letter
	 * untouched if it cannot be converted. Uses a {@code switch} statement
	 * to demonstrate how the {@code case} keyword works.
	 *
	 * @param letter - letter to convert
	 * @return letter converted to 1337-speak
	 */
	public static char toLeetSpeak(char letter) {
		switch (letter) {
		case 'a':
		case 'A':
			if (Math.random() < 0.5) {
				return '4';
			}
			else {
				return '@';
			}
		case 'e':
		case 'E':
			return '3';
		case 'i':
		case 'I':
			return '!';
		case 'l':
		case 'L':
			return '1';
		case 'o':
		case 'O':
			return '0';
		case 's':
		case 'S':
			if (Math.random() < 0.5) {
				return '5';
			}
			else {
				return '$';
			}
		case 't':
		case 'T':
			return '7';
		}

		return letter;
	}

	/**
	 * Randomly converts certain characters to a simple version of
	 * 1337-speak. The provided threshold will determine the percentage
	 * of letters that will attempt to be converted.
	 *
	 * @see #toLeetSpeak(char)
	 * @see #toLeetSpeak(String)
	 * @see #toLeetSpeak(String, double)
	 */
	public static String toLeetSpeak(String text, double threshold) {
		char[] chars = text.toCharArray();

		for (int i = 0; i < chars.length; i++) {
		    // Example of ternary operator
		    // http://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html
			chars[i] = Math.random() < threshold ? toLeetSpeak(chars[i]) : chars[i];
		}

		return String.valueOf(chars);
	}

	/**
	 * Randomly converts certain characters to a simple version of
	 * 1337-speak. Same as {@link #toLeetSpeak(String, double)} with a
	 * threshold of 0.5.
	 *
	 * @see #toLeetSpeak(char)
	 * @see #toLeetSpeak(String)
	 * @see #toLeetSpeak(String, double)
	 */
	public static String toLeetSpeak(String text) {
		return toLeetSpeak(text, 0.5);
	}

	/**
	 * Demonstrates a simple, but memory-intensive way to convert a text
	 * file to 1337-speak.
	 *
	 * @param input - path to the input file
	 * @param output - path to the output file
	 * @throws IOException
	 */
	public static void toLeetSpeakMemoryIntensive(String input, String output) throws IOException {
		Path inputPath = Paths.get(input);
		Path outputPath = Paths.get(output);

		List<String> inputLines =
				Files.readAllLines(inputPath, Charset.defaultCharset());

		List<String> outputLines =
				new ArrayList<String>(inputLines.size());

		for (String line : inputLines) {
			outputLines.add(toLeetSpeak(line));
		}

		Files.write(outputPath, outputLines, Charset.defaultCharset());
	}

	/**
	 * Demonstrates a better way to convert a text file to 1337-speak,
	 * making sure resources are closed and as little memory as possible
	 * is used. Does NOT perform its own exception handling!
	 *
	 * @param input - path to the input file
	 * @param output - path to the output file
	 * @throws IOException
	 */
	public static void toLeetSpeak(String input, String output) throws IOException {
		Path inputPath = Paths.get(input);
		Path outputPath = Paths.get(output);

		try (
			BufferedReader reader =
					Files.newBufferedReader(inputPath, Charset.defaultCharset());
			BufferedWriter writer =
					Files.newBufferedWriter(outputPath, Charset.defaultCharset());
		) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				writer.write(toLeetSpeak(toRandomCase(line)));
				writer.newLine();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String text = "Sally sells seashells at the sea shore.";
		System.out.println(text);
		System.out.println(toRandomCase(text));
		System.out.println(toLeetSpeak(text));
		System.out.println(toLeetSpeak(text, 0.25));
		System.out.println(toLeetSpeak(text, 1.00));
		System.out.println(toLeetSpeak(toRandomCase(text)));

		toLeetSpeak("src/EliteConverter.java", "EliteConverter.txt");
		//toLeetSpeak("nowhere.txt", "nowhere.txt");
	}
}
