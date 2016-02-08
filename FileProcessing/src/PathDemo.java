import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class demonstrates how to use the {@link Path} class to get basic
 * path information.
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
 * @see java.nio.file.Path
 * @see java.nio.file.Paths
 */
public class PathDemo {

	/** Format string used for easy-to-read console output. */
	private static final String format = "%22s: %s%n";

	public static void main(String[] args) {

		/*
		 * Relative paths are nice for users. It is much easier as a user to
		 * enter a relative path instead of an absolute path. However, these
		 * paths are not as useful for our programs. Notice below how much
		 * information is missing.
		 *
		 * Also note that converting to an absolute path doesn't solve all the
		 * problems. There is a extra "." at the end of the path, so there
		 * are still some redundant and unnecessary elements in our path.
		 */
		Path path1 = Paths.get(".");
		System.out.printf(format, "isAbsolute()", path1.isAbsolute());
		System.out.printf(format, "getParent()", path1.getParent());
		System.out.printf(format, "getFileName()", path1.getFileName());
		System.out.printf(format, "toAbsolutePath()", path1.toAbsolutePath());
		System.out.println();

		/*
		 * To convert the path into a format more useful for our programs, use
		 * the toAbsolutePath() and normalize() methods. This will convert
		 * the path to an absolute path, and then remove the redundant parts
		 * of the path. Now, notice how we are able to get more useful details
		 * about our path object.
		 */
		path1 = path1.toAbsolutePath().normalize();
		System.out.printf(format, "isAbsolute()", path1.isAbsolute());
		System.out.printf(format, "getParent()", path1.getParent());
		System.out.printf(format, "getFileName()", path1.getFileName());
		System.out.printf(format, "toString()", path1.toString());
		System.out.println();

		/*
		 * Since Path objects contain hierarchy, we can also get different
		 * elements of that hierarchy.
		 */
		System.out.printf(format, "getRoot()", path1.getRoot());
		System.out.printf(format, "getNameCount()", path1.getNameCount());
		System.out.printf(format, "getName(0)", path1.getName(0));
		System.out.printf(format, "subpath(1, 3)", path1.subpath(0, 3));
		System.out.println();

		/*
		 * You can also easily combine paths together without having to worry
		 * about different path separators on different systems (like "/" on
		 * Unix, Linux, or Mac systems and "\" on Windows systems).
		 */
		Path path2 = Paths.get(".", "src").toAbsolutePath().normalize();
		System.out.printf(format, "path2.toString()", path2.toString());
		System.out.printf(format, "path1.resolve(\"src\")", path1.resolve("src"));
	}
}
