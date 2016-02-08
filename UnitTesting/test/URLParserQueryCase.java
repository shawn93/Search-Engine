/**
 * Part of the {@link URLParser} example. Demonstrates regular expressions,
 * test-driven development, and JUnit test suites.
 *
 * @see URLParser
 * @see URLParserTestCase
 * @see URLParserTestSuite
 */
public class URLParserQueryCase extends URLParserTestCase {

	@Override
	public void setUp() {

		url      = "http://maps.google.com/?ll=37.776609,-122.45077&spn=0.01004,0.013272&t=h&z=17&vpsrc=6";
		protocol = "http";
		domain   = "maps.google.com";
		resource = "/";
		query    = "ll=37.776609,-122.45077&spn=0.01004,0.013272&t=h&z=17&vpsrc=6";
		fragment = null;

		valid    = true;

		super.setUp();
	}
}
