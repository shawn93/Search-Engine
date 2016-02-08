/**
 * Part of the {@link URLParser} example. Demonstrates regular expressions,
 * test-driven development, and JUnit test suites.
 *
 * @see URLParser
 * @see URLParserTestCase
 * @see URLParserTestSuite
 */
public class URLParserHTTPSCase extends URLParserTestCase {

	@Override
	public void setUp() {

		url      = "https://www.wellsfargo.com/";
		protocol = "https";
		domain   = "www.wellsfargo.com";
		resource = "/";
		query    = null;
		fragment = null;

		valid    = true;

		super.setUp();
	}
}
