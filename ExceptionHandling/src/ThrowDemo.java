import java.io.FileInputStream;
import java.io.FileReader;

/**
 * This class demonstrates how to re-throw exceptions. To prevent the stack
 * trace from being printed out to the console for a user, this exception
 * should be eventually caught!
 *
 * <p><em>
 * Note that this class is designed to illustrate a specific concept, and
 * may not be an example of proper class design outside of this context.
 * </em></p>
 *
 * @see
 * <a href="http://docs.oracle.com/javase/tutorial/essential/exceptions/index.html">
 * The Java Tutorials - Lesson: Exceptions
 * </a>
 */
public class ThrowDemo {

	/**
	 * A simple method that purposely causes {@link FileReader} to throw an
	 * exception.
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void a() throws Exception {
		new FileReader("");
	}

	/**
	 * Calls {@link #a()}, and rethrows the exception instead of handling it.
	 * @throws Exception
	 */
	public static void b() throws Exception {
		a();
	}

	/**
	 * Calls {@link #b()}, and rethrows the exception instead of handling it.
	 * @throws Exception
	 */
	public static void c() throws Exception {
		b();
	}

	/**
	 * Calls {@link #c()}, and rethrows the exception. The stack trace for
	 * all exceptions thrown by {@link #main(String[])} will be output to
	 * the console, which is not user-friendly. However, we as developers
	 * can parse through this stack trace to find the bug.
	 *
	 * To read the stack trace, you want to find the earliest line of code
	 * within your file that the exception was generated. In this case, we
	 * do not care that the exception originated in {@link FileInputStream}.
	 * We do care, however, that the exception was thrown in {@link ThrowDemo}.
	 * This is where you should start debugging your code.
	 *
	 * @param args
	 *            unused
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		c();
	}

	/*
	 * Exception in thread "main" java.io.FileNotFoundException:  (No such file or directory)
	 *      at java.io.FileInputStream.open(Native Method)
	 *      at java.io.FileInputStream.<init>(FileInputStream.java:131)
	 *      at java.io.FileInputStream.<init>(FileInputStream.java:87)
	 *      at java.io.FileReader.<init>(FileReader.java:58)
	 *      at ThrowDemo.a(ThrowDemo.java:29)      <-- Look here (origin in YOUR code)
	 *      at ThrowDemo.b(ThrowDemo.java:37)      <-- Look here
	 *      at ThrowDemo.c(ThrowDemo.java:45)      <-- Look here
	 *      at ThrowDemo.main(ThrowDemo.java:65)   <-- Look here
	 */

}
