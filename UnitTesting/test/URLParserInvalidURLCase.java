/**
 * Part of the {@link URLParser} example. Demonstrates regular expressions,
 * test-driven development, and JUnit test suites.
 *
 * @see URLParser
 * @see URLParserTestCase
 * @see URLParserTestSuite
 */
public class URLParserInvalidURLCase extends URLParserTestCase {

	@Override
	public void setUp() {

		url      = "http:///";
		protocol = null;
		domain   = null;
		resource = null;
		query    = null;
		fragment = null;

		valid    = false;

		super.setUp();
	}
}
