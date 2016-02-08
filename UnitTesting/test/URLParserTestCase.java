import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Part of the {@link URLParser} example. Demonstrates regular expressions,
 * test-driven development, and JUnit test suites.
 *
 * @see URLParser
 * @see URLParserTestCase
 * @see URLParserTestSuite
 */
public class URLParserTestCase {
	protected String url;
	protected String protocol;
	protected String domain;
	protected String resource;
	protected String query;
	protected String fragment;

	protected boolean valid;

	protected URLParser parser;

	@Before
	public void setUp() {
		parser = new URLParser(url);
	}

	@After
	public void tearDown() {
		parser = null;
	}

	@Test
	public void testURL() {
		assertEquals(url, parser.url);
	}

	@Test
	public void testValid() {
		assertEquals(url, valid, parser.valid);
	}

	@Test
	public void testProtocol() {
		assertEquals(url, protocol, parser.protocol);
	}

	@Test
	public void testDomain() {
		assertEquals(url, domain, parser.domain);
	}

	@Test
	public void testResource() {
		assertEquals(url, resource, parser.resource);
	}

	@Test
	public void testQuery() {
		assertEquals(url, query, parser.query);
	}

	@Test
	public void testFragment() {
		assertEquals(url, fragment, parser.fragment);
	}
}
