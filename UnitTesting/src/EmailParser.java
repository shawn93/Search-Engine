import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses an email into its local, domain, and top-level domain components.
 * Does not do full email validation!
 *
 * Demonstrates regular expressions and unit testing.
 *
 * @see EmailParser
 * @see EmailParserTest
 */
public class EmailParser {
    /** The entire email (null if invalid), like "sjengle@cs.usfca.edu". */
	private String email;

	/** The local portion of the email, like "sjengle". **/
	private String local;

	/** The domain of the email, like "cs.usfca.edu". **/
	private String domain;

	/**
	 * The top-level domain of the email, like "edu".
	 **/
	private String tld;

	/** Indicates whether the email provided was parsable. **/
	private boolean valid;

	/**
	 * Initializes the individual components of an email.
	 *
	 * @param email - email address to parse
	 */
	public EmailParser(String email) {
		if (email == null) {
			return;
		}

        /*
         * Try the following regular expressions, and see which ones pass and
         * fail. Make sure you understand both the regex, and the unit tests.
         *
         * There may be multiple regular expressions that will pass the provided
         * tests. Try out your own!
         */

		String regex = null;

//		regex = "(.+)@(.+\\.(.+))";
//		regex = "([^@]*)@([^@]*\\.([^@]*))";
		regex = "^([^@]+)@([^@]+\\.([^.@]+))$";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);

		/*
		 * Use matches() instead of find() to match against the entire string.
		 * This assumes we have ONLY the email address, and are not trying
		 * to find an email address within other text.
		 */

		if (!m.matches()) {
			valid = false;
			return;
		}

		assert m.groupCount() == 3 : "Groups: " + m.groupCount();

		valid = true;

		this.email  = m.group(0);
		this.local  = m.group(1);
		this.domain = m.group(2);
		this.tld    = m.group(3);
	}

	/*
	 * No other set methods are necessary, as we only need to set the email
	 * and that is taken care of by the constructor.
	 */

	/**
	 * Determines whether the email could be parsed.
	 * @return true if the email coule be parsed
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Returns the local component of an email.
	 * @return the local component of the email
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * Returns the domain component of an email.
	 * @return the domain component of the email
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Returns the top-level domain (without period).
	 * @return the top-level domain of the email
	 */
	public String getTLD() {
		return tld;
	}

	/**
	 * Returns the entire email (if it could be parsed).
	 * @return the email, or null if could not be parsed
	 */
	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return getEmail();
	}

	/**
	 * Returns all of the email components as parsed by the regular expression.
	 * @return debug output
	 */
	public String debug() {
		return String.format("Email: %s, Local: %s, Domain: %s, TLD: %s",
				getEmail(), getLocal(), getDomain(), getTLD());
	}
}
