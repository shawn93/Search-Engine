import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Part of the {@link URLParser} example. Demonstrates regular expressions,
 * test-driven development, and JUnit test suites.
 *
 * @see URLParser
 * @see URLParserTestCase
 * @see URLParserTestSuite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	URLParserCompleteCase.class,
	URLParserFragmentCase.class,
	URLParserFTPCase.class,
	URLParserHTTPSCase.class,
	URLParserInvalidDomainCase.class,
	URLParserInvalidProtocolCase.class,
	URLParserInvalidURLCase.class,
	URLParserNullCase.class,
	URLParserQueryCase.class,
	URLParserSimpleCase.class
})
public class URLParserTestSuite {

}
