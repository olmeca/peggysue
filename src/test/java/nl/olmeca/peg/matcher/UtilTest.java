package nl.olmeca.peg.matcher;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    public void testCharsetCharMatch() throws NoMatchException {
        List<Match> matches = Util.matchList("abc", DSL.charsetChar);
        assertEquals(3, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
        assertEquals("c", matches.get(2).getValueString());
    }

    @Test
    public void testCharsetCharMatchEndBracket() throws NoMatchException {
        List<Match> matches = Util.matchList("ab]", DSL.charsetChar);
        assertEquals(2, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
    }

    @Test
    public void testCharsetCharMatchWithSep() throws NoMatchException {
        Matcher sepMatcher = new GivenChar(',');
        List<Match> matches = Util.matchList("a,b,c", DSL.charsetChar, sepMatcher);
        assertEquals(3, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
        assertEquals("c", matches.get(2).getValueString());
    }

    @Test
    public void testCharsetCharMatchWithSep2() throws NoMatchException {
        Matcher sepMatcher = new OneOrMore(new GivenChar(' '));
        List<Match> matches = Util.matchList("a    b  c", DSL.charsetChar, sepMatcher);
        assertEquals(3, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
        assertEquals("c", matches.get(2).getValueString());
    }

    @Test
    public void testCharsetCharMatchEscapedChar() throws NoMatchException {
        List<Match> matches = Util.matchList("\\2c5", DSL.charsetChar);
        assertEquals(3, matches.size());
        assertEquals("\\2", matches.get(0).getValueString());
        assertEquals("c", matches.get(1).getValueString());
        assertEquals("5", matches.get(2).getValueString());
    }
}