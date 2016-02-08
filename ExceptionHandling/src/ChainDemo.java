/**
 * This class demonstrates how to chain together exceptions.
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
public class ChainDemo {

	/**
	 * A simple method that throws an exception.
	 * @throws Exception
	 */
	public static void a() throws Exception {
		throw new Exception("a");
	}

	/**
	 * A simple method that attempts to call {@link ChainDemo#a()}. Since
	 * this method fails because of an exception in the other method, we
	 * will chain together those exceptions.
	 *
	 * @throws Exception
	 */
	public static void b() throws Exception {
		try {
			a();
		}
		catch (Exception e) {
			/*
			 * Since this exception is "caused by" the one thrown in a(),
			 * we will chain together these exceptions.
			 */
			throw new Exception("b", e);
		}
	}

	/**
	 * A simple method that attempts to call {@link #b()}. Since
	 * this method fails because of an exception in the other method, we
	 * will chain together those exceptions.
	 *
	 * @throws Exception
	 */
	public static void c() throws Exception {
		try {
			b();
		}
		catch (Exception e) {
			/*
			 * Since this exception is "caused by" the one thrown in b(),
			 * we will chain together these exceptions.
			 */
			throw new Exception("c", e);
		}
	}

	/**
	 * Calls method {@link #c()}, causing a cascading chaining of exceptions.
	 * Note that the output shows that the exception in {@link #c()} was
	 * caused by the exception in {@link #b()}, which in turn was caused by
	 * the exception in {@link #a()}.
	 *
	 * @param args unused
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		c();
	}

}
