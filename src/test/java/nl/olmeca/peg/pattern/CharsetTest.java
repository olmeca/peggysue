package nl.olmeca.peg.pattern;

import nl.olmeca.peg.parser.Peg;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.olmeca.peg.parser.Peg.charsetChar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class CharsetTest {

    @Test
    public void testNormalChar() throws NoMatchException {
        charsetChar.match("a");
    }

    @Test
    public void testEscapedChar() throws NoMatchException {
        charsetChar.match("\\b");
    }

    @Test
    public void testRightSquaredBracket() {
        NoMatchException ex = assertThrowsExactly(NoMatchException.class,
                () -> charsetChar.match("]")
        );
    }

    @Test
    public void testEscapedChar2() throws NoMatchException {
        charsetChar.match("\\]");
    }

    @Test
    void testCharsetWithGivenChar() throws NoMatchException {
        Match match = Peg.charset().match("[a]");
        List<Match> captures = match.getCapturedMatches();
        assertEquals(1, captures.size());
        assertEquals(1, captures.get(0).length);
        assertEquals("a", captures.get(0).getValueString());
    }

    @Test
    void testCharsetWithEscaped() throws NoMatchException {
        List<Match> captures = Peg.charset().match("[\\a]").getCapturedMatches();
        assertEquals(1, captures.size());
        assertEquals(2, captures.get(0).length);
        assertEquals("\\a", captures.get(0).getValueString());
    }

    @Test
    void testCharsetWithEscapedAndGivenChar() throws NoMatchException {
        Match match = Peg.charset().match("[b\\a]");
        List<Match> captures = match.getCapturedMatches();
        assertEquals(1, captures.size());
        assertEquals(3, captures.get(0).length);
        assertEquals("b\\a", captures.get(0).getValueString());
    }
}
