import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class demonstrates how to use the {@link Files} class to get basic
 * file information.
 *
 * <p><em>
 * Note that this class is designed to illustrate a specific concept, and
 * may not be an example of proper class design outside of this context.
 * </em></p>
 *
 * @see
 * <a href="http://docs.oracle.com/javase/tutorial/essential/io/index.html">
 * The Java Tutorials - Lesson: Basic I/O
 * </a>
 *
 * @see java.nio.file.Files
 */
public class FilesDemo {

	/**
	 * Demonstrates the {@link Files} class.
	 * @throws Exception if the source code for {@link FilesDemo} is not
	 * accessible in the src subdirectory.
	 */
	public static void main(String[] args) throws Exception {

		// Assumes source code is located in the src subdirectory.
		Path path = Paths.get("src", "FilesDemo.java").toAbsolutePath();

		// Get basic information about the file.
		System.out.println(" File Name: " + path.getFileName());
		System.out.println(" File Size: " + Files.size(path) + " bytes");
		System.out.println("File Owner: " + Files.getOwner(path));
		System.out.println("  Modified: " + Files.getLastModifiedTime(path));
		System.out.println();

		// Check whether the file is a directory or hidden.
		System.out.println("    Exists? " + Files.exists(path));
		System.out.println(" Directory? " + Files.isDirectory(path));
		System.out.println("    Hidden? " + Files.isHidden(path));
		System.out.println();

		// Check the read/write/execute permissions of the file.
		System.out.println("Executable? " + Files.isExecutable(path));
		System.out.println("  Readable? " + Files.isReadable(path));
		System.out.println("  Writable? " + Files.isWritable(path));
	}
}
