package nl.olmeca.peg.matcher;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CharsetParserTest {


    @Test
    public void testParseCharset1() throws NoMatchException {
        Match match = Peg.charset().match("[b\\']");
        Optional<Matcher> optionalMatcher = CharsetParser.parseCharset(match);
        assertTrue(optionalMatcher.isPresent());
        Matcher matcher = optionalMatcher.get();
        match = matcher.match("ba");
        assertEquals(1, match.length);
    }

    @Test
    public void testParseCharsetWithRange() throws NoMatchException {
        Optional<Matcher> optionalMatcher = CharsetParser.parseCharset("[zb-d]");
        assertTrue(optionalMatcher.isPresent());
        Matcher matcher = optionalMatcher.get();
        assertEquals(1,matcher.match("za").length);
        assertEquals(1,matcher.match("bg").length);
        assertEquals(1,matcher.match("cg").length);
        assertEquals(1,matcher.match("dg").length);
        assertThrowsExactly(NoMatchException.class, () -> matcher.match("eb"));
        assertThrowsExactly(NoMatchException.class, () -> matcher.match("ab"));
    }

    @Test
    public void testParseEscapedChars() throws NoMatchException {
        Optional<Matcher> optionalMatcher = CharsetParser.parseCharset("[\\]a]");
        assertTrue(optionalMatcher.isPresent());
        Matcher matcher = optionalMatcher.get();
        assertEquals(1,matcher.match("]p").length);
        assertEquals(1,matcher.match("ap").length);

    }

    @Test
    public void testParseEscapedWhiteSpaceChars() throws NoMatchException {
        Optional<Matcher> optionalMatcher = CharsetParser.parseCharset("[\\na]");
        assertTrue(optionalMatcher.isPresent());
        Matcher matcher = optionalMatcher.get();
        char[] source = new char[] { '\n', 'b', 'c', 'd', 'e', 'f' };
        Match match = matcher.match(source);
        assertEquals(1, match.length);
        assertEquals(1,matcher.match("ap").length);

    }

    @Test
    public void testParseNegatedChar() throws NoMatchException {
        Optional<Matcher> optionalMatcher = CharsetParser.parseCharset("[^a]");
        assertTrue(optionalMatcher.isPresent());
        Matcher matcher = optionalMatcher.get();
        assertEquals(1,matcher.match("za").length);
        assertThrowsExactly(NoMatchException.class, () -> matcher.match("ab"));
    }
    @Test
    public void testParseNegatedCharRange() throws NoMatchException {
        Optional<Matcher> optionalMatcher = CharsetParser.parseCharset("[^b-df]");
        assertTrue(optionalMatcher.isPresent());
        Matcher matcher = optionalMatcher.get();
        assertEquals(1,matcher.match("ap").length);
        assertEquals(1,matcher.match("ep").length);
        assertThrowsExactly(NoMatchException.class, () -> matcher.match("bp"));
    }

    @Test
    public void testComment() throws NoMatchException {
        char[] source = new char[] {'#', 'a', '\n', 'b' };
        Match match = Peg.comment.match(source);
        assertEquals(3, match.length);
    }

    @Test
    public void testCommentNoLineSep() throws NoMatchException {
        char[] source = new char[] {'#', 'a' };
        Match match = Peg.comment.match(source);
        assertEquals(2, match.length);
    }
}
