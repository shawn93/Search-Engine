import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Calculates the total number of files and bytes found in one or more
 * directories, and their subdirectories. Demonstrates multithreading and how
 * to use a work queue.
 *
 * Please note this class is designed to illustrate a specific concept, and is
 * not an example of good class design outside of this context.
 *
 * @see DirectorySizeCalculator
 * @see MultithreadedDirectorySizeCalculator
 *
 * @author Sophie Engle
 * @author CS 212 Software Development
 * @author University of San Francisco
 */
public class DirectorySizeCalculator {

	private static final Logger logger = LogManager.getLogger();

	private long files;
	private long bytes;

	/**
	 * Initializes the number of files found and total bytes found to 0.
	 */
	public DirectorySizeCalculator() {
		this.files = 0;
		this.bytes = 0;
	}


	/**
	 * Resets the counters, allowing this object to be easily reused if desired.
	 */
	public void reset() {
		this.bytes = 0;
		this.files = 0;
		logger.debug("Counters reset");
	}

	/**
	 * Returns the number of files found since the last reset.
	 *
	 * @return number of files
	 */
	public long getFiles() {
		logger.debug("Getting files");
		return this.files;
	}

	/**
	 * Returns the number of bytes found since the last reset.
	 *
	 * @return number of bytes
	 */
	public long getBytes() {
		logger.debug("Getting bytes");
		return this.bytes;
	}

	/**
	 * Adds the number of files and bytes in the specified directory to the
	 * current total.
	 *
	 * @param directory
	 */
	public void addDirectory(Path directory) {
		try {
			if (Files.isDirectory(directory)) {
				processDirectory(directory);
			}
			else if (Files.exists(directory)) {
				updateCounters(1, Files.size(directory));
			}
		}
		catch (IOException e) {
			logger.warn("Unable to calculate size for {}", directory);
			logger.catching(Level.DEBUG, e);
		}
	}

	/**
	 * Handles per-directory parsing. If a subdirectory is encountered, a
	 * recursive call will handle that subdirectory.
	 */
	private void processDirectory(Path directory) {
		assert Files.isDirectory(directory);

		logger.debug("Processing {}", directory);

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			for (Path path : stream) {
				if (Files.isDirectory(path)) {
					processDirectory(path);
				}
				else {
					updateCounters(1, Files.size(path));
				}
			}
		}
		catch (IOException e) {
			logger.warn("Unable to parse {}", directory);
			logger.catching(Level.DEBUG, e);
		}
	}

	/**
	 * Updates the number of files and bytes found. Not entirely necessary
	 * for this example, but becomes useful when we move to multithreading.
	 *
	 * @param files
	 * @param bytes
	 */
	private void updateCounters(long files, long bytes) {
		assert files >= 0;
		assert bytes >= 0;

		this.files += files;
		this.bytes += bytes;
		logger.debug("Counters are {} files and {} bytes", this.files, this.bytes);
	}

	/**
	 * Runs a simple example to demonstrate this class. Try changing the path
	 * to your root directory, and see how long it takes!
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		DirectorySizeCalculator demo = new DirectorySizeCalculator();
		demo.addDirectory(Paths.get("."));

		System.out.println(demo.getFiles() + " files");
		System.out.println(demo.getBytes() + " bytes");
	}
}
