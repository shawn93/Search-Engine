/**
 * Part of the {@link URLParser} example. Demonstrates regular expressions,
 * test-driven development, and JUnit test suites.
 *
 * @see URLParser
 * @see URLParserTestCase
 * @see URLParserTestSuite
 */
public class URLParserFTPCase extends URLParserTestCase {

	@Override
	public void setUp() {

		url      = "ftp://ftp.chiark.greenend.org.uk/users/sgtatham/putty-latest/x86/putty.exe";
		protocol = "ftp";
		domain   = "ftp.chiark.greenend.org.uk";
		resource = "/users/sgtatham/putty-latest/x86/putty.exe";
		query    = null;
		fragment = null;

		valid    = true;

		super.setUp();
	}
}
