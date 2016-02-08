/**
 * Part of the {@link URLParser} example. Demonstrates regular expressions,
 * test-driven development, and JUnit test suites.
 *
 * @see URLParser
 * @see URLParserTestCase
 * @see URLParserTestSuite
 */
public class URLParserInvalidProtocolCase extends URLParserTestCase {

	@Override
	public void setUp() {

		url      = "www.google.com";
		protocol = null;
		domain   = null;
		resource = null;
		query    = null;
		fragment = null;

		valid    = false;

		super.setUp();
	}
}
