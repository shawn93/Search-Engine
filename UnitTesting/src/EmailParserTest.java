import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Parses an email into its local, domain, and top-level domain components.
 * Does not do full email validation.
 *
 * Demonstrates regular expressions and unit testing.
 *
 * @see EmailParser
 * @see EmailParserTest
 */
public class EmailParserTest {
	/*
	 * The following tests focus on a simple email, sjengle@usfca.edu,
	 * and make sure parsing works.
	 */

	@Test
	public void testSimpleValid() {
		EmailParser parser = new EmailParser("sjengle@usfca.edu");
		assertTrue(parser.debug(), parser.isValid());
	}

	@Test
	public void testSimpleLocal() {
		EmailParser parser = new EmailParser("sjengle@usfca.edu");
		assertEquals(parser.debug(), "sjengle", parser.getLocal());
	}

	@Test
	public void testSimpleDomain() {
		EmailParser parser = new EmailParser("sjengle@usfca.edu");
		assertEquals(parser.debug(), "usfca.edu", parser.getDomain());
	}

	@Test
	public void testSimpleTLD() {
		EmailParser parser = new EmailParser("sjengle@usfca.edu");
		assertEquals(parser.debug(), "edu", parser.getTLD());
	}

	/*
	 * The following tests focus on a email with a subdomain,
	 * sjengle@cs.usfca.edu, and makes sure parsing works.
	 */

	@Test
	public void testSubdomainValid() {
		EmailParser parser = new EmailParser("sjengle@cs.usfca.edu");
		assertTrue(parser.debug(), parser.isValid());
	}

	@Test
	public void testSubdomainLocal() {
		EmailParser parser = new EmailParser("sjengle@cs.usfca.edu");
		assertEquals(parser.debug(), "sjengle", parser.getLocal());
	}

	@Test
	public void testSubdomainDomain() {
		EmailParser parser = new EmailParser("sjengle@cs.usfca.edu");
		assertEquals(parser.debug(), "cs.usfca.edu", parser.getDomain());
	}

	@Test
	public void testSubdomainTLD() {
		EmailParser parser = new EmailParser("sjengle@cs.usfca.edu");
		assertEquals(parser.debug(), "edu", parser.getTLD());
	}

	/*
	 * The following tests focus on a complex email,
	 * aaa.bb+0123@c45.6-d.ee, and makes sure parsing works.
	 */

	@Test
	public void testComplexValid() {
		EmailParser parser = new EmailParser("aaa.bb+0123@c45.6-d.ee");
		assertTrue(parser.debug(), parser.isValid());
	}

	@Test
	public void testComplexLocal() {
		EmailParser parser = new EmailParser("aaa.bb+0123@c45.6-d.ee");
		assertEquals(parser.debug(), "aaa.bb+0123", parser.getLocal());
	}

	@Test
	public void testComplexDomain() {
		EmailParser parser = new EmailParser("aaa.bb+0123@c45.6-d.ee");
		assertEquals(parser.debug(), "c45.6-d.ee", parser.getDomain());
	}

	@Test
	public void testComplexTLD() {
		EmailParser parser = new EmailParser("aaa.bb+0123@c45.6-d.ee");
		assertEquals(parser.debug(), "ee", parser.getTLD());
	}

	/*
	 * The following tests focus on making sure the emails are
	 * considered valid.
	 */

	@Test
	public void testValidManyDots() {
		EmailParser parser = new EmailParser("aa.bb.cccc@dd.eeee.f");
		assertTrue(parser.debug(), parser.isValid());
	}

	@Test
	public void testValidDigits() {
		EmailParser parser = new EmailParser("0123@4567.aaa");
		assertTrue(parser.debug(), parser.isValid());
	}

	@Test
	public void testValidSymbols() {
		EmailParser parser = new EmailParser("aa+bb#cc@dddd.e");
		assertTrue(parser.debug(), parser.isValid());
	}

	@Test
	public void testValidShort() {
		EmailParser parser = new EmailParser("a@b.c");
		assertTrue(parser.debug(), parser.isValid());
	}

	/*
	 * The following tests focus on making sure the emails are not
	 * considered valid.
	 */

	@Test
	public void testInvalidNoAt() {
		EmailParser parser = new EmailParser("aaaa");
		assertFalse(parser.debug(), parser.isValid());
	}

	@Test
	public void testInvalidNoDomain() {
		EmailParser parser = new EmailParser("aaaa@");
		assertFalse(parser.debug(), parser.isValid());
	}

	@Test
	public void testInvalidNoLocal() {
		EmailParser parser = new EmailParser("@bbbb.ccc");
		assertFalse(parser.debug(), parser.isValid());
	}

	@Test
	public void testInvalidNoTLD() {
		EmailParser parser = new EmailParser("aaaa@bbbb");
		assertFalse(parser.debug(), parser.isValid());
	}

	@Test
	public void testInvalidTooManyAts() {
		EmailParser parser = new EmailParser("aaaa@bbbb@cccc.ddd");
		assertFalse(parser.debug(), parser.isValid());
	}
}
