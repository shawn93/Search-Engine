import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Compare two files, returning the difference in the number of characters
 * using the {@link CharacterCounter#countCharacters(Path)} method.
 *
 * This method does not perform any exception handling for simplicity! If
 * there are any issues reading the files, the result will be meaningless.
 *
 *
 * @see {@link CharacterCounter}
 * @see {@link CharacterCompare}
 * @see {@link CharacterBenchmark}
 */
public class CharacterBenchmark {

	/**
	 * Compares sequential versus concurrent comparison of files. Assumes the
	 * files pg1661.txt and pg2701.txt are located in the "text" subdirectory.
	 *
	 * @param args unused
	 * @throws Exception if files are not readable
	 */
	public static void main(String[] args) throws Exception {
		int warmup = 3;
		int runs = 10;

		// Use two fairly large files to test code.
		Path file1 = Paths.get("text", "pg1661.txt");
		Path file2 = Paths.get("text", "pg2701.txt");

		long start = 0;
		long elapsed = 0;
		double average = 0;

		/*
		 * Benchmarking in Java is difficult due to optimization and garbage
		 * collection. As such, the first run is almost always longer than
		 * all subsequent runs. We could try to call the garbage collector
		 * to prevent any optimization after the first run by calling the
		 * System.gc() method, but this only SUGGESTS that garbage collection
		 * should be run at that time.
		 *
		 * See the following article for more details:
		 * http://www.ibm.com/developerworks/java/library/j-benchmark2/index.html
		 *
		 * Nevertheless, I do want to illustrate the speedup we could get from
		 * multithreading, so here are some simple benchmarks to take with a
		 * grain of salt.
		 *
		 * Make sure you DISABLE LOGGING before running this benchmark!
		 */

		// "Warm up" code, loading classes as necessary. Compare times with
		// and without the warmup part!
		for (int i = 0; i < warmup; i++) {
			CharacterCompare.compareSequentially(file1, file2);
		}

		// Average how long it takes to run sequentially over several runs.
		for (int i = 0; i < runs; i++) {
			start = System.nanoTime();
			CharacterCompare.compareSequentially(file1, file2);
			elapsed = System.nanoTime() - start;
			average += elapsed;
		}

		// Take average and convert from nanoseconds to seconds.
		average /= runs;
		average /= 1000000000;

		System.out.printf("Took %.05f seconds average for sequential comparison.%n", average);

		// Reset average value for next set of calculations.
		average = 0;

		// "Warm up" code, loading classes as necessary. Compare times with
		// and without the warmup part!
		for (int i = 0; i < warmup; i++) {
			CharacterCompare.compareConcurrently(file1, file2);
		}

		// Average how long it takes to run concurrently over several runs.
		for (int i = 0; i < runs; i++) {
			start = System.nanoTime();
			CharacterCompare.compareConcurrently(file1, file2);
			elapsed = System.nanoTime() - start;
			average += elapsed;
		}

		// Take average and convert from nanoseconds to seconds.
		average /= runs;
		average /= 1000000000;
		System.out.printf("Took %.05f seconds average for concurrent comparison.%n", average);
	}
}