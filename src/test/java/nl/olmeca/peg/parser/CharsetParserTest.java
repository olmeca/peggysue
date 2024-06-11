package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Match;
import nl.olmeca.peg.pattern.Pattern;
import nl.olmeca.peg.pattern.NoMatchException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CharsetParserTest {


    @Test
    public void testParseCharset1() throws NoMatchException {
        Match match = Peg.charset().match("[b\\']");
        Optional<Pattern> optionalMatcher = CharsetParser.parse(match);
        assertTrue(optionalMatcher.isPresent());
        Pattern pattern = optionalMatcher.get();
        match = pattern.match("ba");
        assertEquals(1, match.getLength());
    }

    @Test
    public void testParseCharsetWithRange() throws NoMatchException {
        Optional<Pattern> optionalMatcher = CharsetParser.parse("[zb-d]");
        assertTrue(optionalMatcher.isPresent());
        Pattern pattern = optionalMatcher.get();
        assertEquals(1, pattern.match("za").getLength());
        assertEquals(1, pattern.match("bg").getLength());
        assertEquals(1, pattern.match("cg").getLength());
        assertEquals(1, pattern.match("dg").getLength());
        assertThrowsExactly(NoMatchException.class, () -> pattern.match("eb"));
        assertThrowsExactly(NoMatchException.class, () -> pattern.match("ab"));
    }

    @Test
    public void testParseEscapedChars() throws NoMatchException {
        Optional<Pattern> optionalMatcher = CharsetParser.parse("[\\]a]");
        assertTrue(optionalMatcher.isPresent());
        Pattern pattern = optionalMatcher.get();
        assertEquals(1, pattern.match("]p").getLength());
        assertEquals(1, pattern.match("ap").getLength());

    }

    @Test
    public void testParseEscapedWhiteSpaceChars() throws NoMatchException {
        Optional<Pattern> optionalMatcher = CharsetParser.parse("[\\na]");
        assertTrue(optionalMatcher.isPresent());
        Pattern pattern = optionalMatcher.get();
        char[] source = new char[] { '\n', 'b', 'c', 'd', 'e', 'f' };
        Match match = pattern.match(source);
        assertEquals(1, match.getLength());
        assertEquals(1, pattern.match("ap").getLength());

    }

    @Test
    public void testParseNegatedChar() throws NoMatchException {
        Optional<Pattern> optionalMatcher = CharsetParser.parse("[^a]");
        assertTrue(optionalMatcher.isPresent());
        Pattern pattern = optionalMatcher.get();
        assertEquals(1, pattern.match("za").getLength());
        assertThrowsExactly(NoMatchException.class, () -> pattern.match("ab"));
    }
    @Test
    public void testParseNegatedCharRange() throws NoMatchException {
        Optional<Pattern> optionalMatcher = CharsetParser.parse("[^b-df]");
        assertTrue(optionalMatcher.isPresent());
        Pattern pattern = optionalMatcher.get();
        assertEquals(1, pattern.match("ap").getLength());
        assertEquals(1, pattern.match("ep").getLength());
        assertThrowsExactly(NoMatchException.class, () -> pattern.match("bp"));
    }

    @Test
    public void testComment() throws NoMatchException {
        char[] source = new char[] {'#', 'a', '\n', 'b' };
        Match match = Peg.comment.match(source);
        assertEquals(3, match.getLength());
    }

    @Test
    public void testCommentNoLineSep() throws NoMatchException {
        char[] source = new char[] {'#', 'a' };
        Match match = Peg.comment.match(source);
        assertEquals(2, match.getLength());
    }
}
