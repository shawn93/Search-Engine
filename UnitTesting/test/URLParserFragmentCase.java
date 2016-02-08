/**
 * Part of the {@link URLParser} example. Demonstrates regular expressions,
 * test-driven development, and JUnit test suites.
 *
 * @see URLParser
 * @see URLParserTestCase
 * @see URLParserTestSuite
 */
public class URLParserFragmentCase extends URLParserTestCase  {

	@Override
	public void setUp() {

		url      = "http://en.wikipedia.org/wiki/University_of_san_francisco#Notable_alumni_and_faculty";
		protocol = "http";
		domain   = "en.wikipedia.org";
		resource = "/wiki/University_of_san_francisco";
		query    = null;
		fragment = "Notable_alumni_and_faculty";

		valid    = true;

		super.setUp();
	}
}
