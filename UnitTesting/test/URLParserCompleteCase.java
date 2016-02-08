/**
 * Part of the {@link URLParser} example. Demonstrates regular expressions,
 * test-driven development, and JUnit test suites.
 *
 * @see URLParser
 * @see URLParserTestCase
 * @see URLParserTestSuite
 */
public class URLParserCompleteCase extends URLParserTestCase {

	@Override
	public void setUp() {

		url      = "http://docs.python.org/library/string.html?highlight=string#module-string";
		protocol = "http";
		domain   = "docs.python.org";
		resource = "/library/string.html";
		query    = "highlight=string";
		fragment = "module-string";

		valid    = true;

		super.setUp();
	}
}
