import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * You must fill in 5 tests in this class according to the documentation
 * provided. This involves specifying a test case and the expected output.
 * The rest of the test code as already been provided for you.
 *
 * <p><strong>
 * You cannot replicate any of the tests already provided for you in
 * {@link HTMLLinkParserTest}! You must provide new and unique test cases.
 * </p></strong>
 *
 * @see HTMLLinkParser
 * @see HTMLLinkParserTest
 * @see HTMLLinkParserExtraTest
 */
public class HTMLLinkParserExtraTest {

    /**
     * Tests a simple html snippet (with 1 to 2 tags) that contains no
     * links (a link being the text specified as the "href" attribute of
     * an anchor "a" tag).
     */
    @Test
    public void testSimpleHTMLNoLinks() {
    	
        String test = "<a> href = \"www.nolink.com\"</a>";

        ArrayList<String> expected = new ArrayList<String>();
        ArrayList<String> actual = HTMLLinkParser.listLinks(test);

        assertEquals(expected, actual);
    }

    /**
     * Tests a simple html snippet (with 1 to 2 tags) that contains one
     * link (a link being the text specified as the "href" attribute of
     * an anchor "a" tag).
     */
    @Test
    public void testSimpleHTMLOneLink() {

        String test = "<a href=\"https://www.youtube.com\">";

        String link = "https://www.youtube.com";

        ArrayList<String> expected = new ArrayList<String>();
        expected.add(link);

        ArrayList<String> actual = HTMLLinkParser.listLinks(test);

        assertEquals(expected, actual);
    }

    /**
     * Tests a simple html snippet (with 1 to 2 tags) that contains two
     * links (a link being the text specified as the "href" attribute of
     * an anchor "a" tag).
     */
    @Test
    public void testSimpleHTMLTwoLinks() {
    	
        String test = "<A name= web>" + "<a href=\"https://www.sina.com\">" + "<a href=\"https://www.baidu.com\">";

        String link1 = "https://www.sina.com";
        String link2 = "https://www.baidu.com";

        ArrayList<String> expected = new ArrayList<String>();
        expected.add(link1);
        expected.add(link2);

        ArrayList<String> actual = HTMLLinkParser.listLinks(test);

        assertEquals(expected, actual);
    }

    /**
     * Tests a complex html snippet (with 3 to 5 tags and newlines) that
     * contains no links (a link being the text specified as the "href"
     * attribute of an anchor "a" tag).
     */
    @Test
    public void testComplexHTMLNoLinks() {

        String test = "<a title=href>The href = \"youtube.com/watch?v=k8q2NoVdN9Q\" attribute is useful.</a>";

        ArrayList<String> expected = new ArrayList<String>();
        ArrayList<String> actual = HTMLLinkParser.listLinks(test);

        assertEquals(expected, actual);
    }

    /**
     * Tests a complex html snippet (with 3 to 5 tags and newlines) that
     * contains two links (a link being the text specified as the "href"
     * attribute of an anchor "a" tag).
     */
    @Test
    public void testComplexHTMLTwoLinks() {

        String test = "<a hRef    =\"http://www.acronymfinder.com/~/search/af.aspx?Acronym=RSS&Category=Org&Page=0&string=&s=a\"    >" + 
        "<A\n href     =\"http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html\"         >";

        String link1 = "http://www.acronymfinder.com/~/search/af.aspx?Acronym=RSS&Category=Org&Page=0&string=&s=a";
        String link2 = "http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html";

        ArrayList<String> expected = new ArrayList<String>();
        expected.add(link1);
        expected.add(link2);

        ArrayList<String> actual = HTMLLinkParser.listLinks(test);

        assertEquals(expected, actual);
    }
}
