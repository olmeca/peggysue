package nl.olmeca.peg.pattern;

import nl.olmeca.peg.parser.Peg;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    public void testCharsetCharMatch() throws NoMatchException {
        List<Match> matches = Util.matchList("abc", Peg.charsetChar());
        assertEquals(3, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
        assertEquals("c", matches.get(2).getValueString());
    }

    @Test
    public void testCharsetCharMatchEndBracket() throws NoMatchException {
        List<Match> matches = Util.matchList("ab\\]", Peg.charsetChar());
        assertEquals(3, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
        assertEquals("\\]", matches.get(2).getValueString());
    }

    @Test
    public void testCharsetCharMatchWithSep() throws NoMatchException {
        Pattern sepPattern = new GivenChar(',');
        List<Match> matches = Util.matchList("a,b,c", Peg.charsetChar(), sepPattern);
        assertEquals(3, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
        assertEquals("c", matches.get(2).getValueString());
    }

    @Test
    public void testCharsetCharMatchWithSep2() throws NoMatchException {
        Pattern sepPattern = new OneOrMore(new GivenChar(' '));
        List<Match> matches = Util.matchList("a    b  c", Peg.charsetChar(), sepPattern);
        assertEquals(3, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
        assertEquals("c", matches.get(2).getValueString());
    }

    @Test
    public void testCharsetCharMatchEscapedChar() throws NoMatchException {
        List<Match> matches = Util.matchList("\\2c5", Peg.charsetChar());
        assertEquals(3, matches.size());
        assertEquals("\\2", matches.get(0).getValueString());
        assertEquals("c", matches.get(1).getValueString());
        assertEquals("5", matches.get(2).getValueString());
    }

    @Test
    public void testPrimaryIdentifiers() throws NoMatchException {
        List<Match> matches = Util.matchList("&a !b c", Peg.primary);
        assertEquals(3, matches.size());
        assertEquals("&a", matches.get(0).getValueString());
        assertEquals(" !b", matches.get(1).getValueString());
        assertEquals(" c", matches.get(2).getValueString());
    }

    @Test
    public void testPrimaryIdentifiersWithPrefix() throws NoMatchException {
        List<Match> matches = Util.matchList("a b c", Peg.primary);
        assertEquals(3, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals(" b", matches.get(1).getValueString());
        assertEquals(" c", matches.get(2).getValueString());
    }

    @Test
    public void testPrimaryIdentifiersWithPrefixAndPostfix() throws NoMatchException {
        List<Match> matches = Util.matchList("&a+ !b*", Peg.primary);
        assertEquals(2, matches.size());
        assertEquals("&a+", matches.get(0).getValueString());
        assertEquals(" !b*", matches.get(1).getValueString());
    }

    @Test
    public void testPrimaryIdentifiersWithPrefixAndPostfix2() throws NoMatchException {
        List<Match> matches = Util.matchList("& a + ! b *", Peg.primary);
        assertEquals(2, matches.size());
        assertEquals("& a +", matches.get(0).getValueString());
        assertEquals(" ! b *", matches.get(1).getValueString());
    }

    @Test
    public void testPrimaryCharsetWithPrefixAndPostfix() throws NoMatchException {
        List<Match> matches = Util.matchList("&[^a-z]+ ![0-9]*", Peg.primary);
        assertEquals(2, matches.size());
        assertEquals("&[^a-z]+", matches.get(0).getValueString());
        assertEquals(" ![0-9]*", matches.get(1).getValueString());
    }

    @Test
    public void testPrimaryIdentifiersWithPostfix() throws NoMatchException {
        List<Match> matches = Util.matchList("a+ b* c?", Peg.primary);
        assertEquals(3, matches.size());
        assertEquals("a+", matches.get(0).getValueString());
        assertEquals(" b*", matches.get(1).getValueString());
        assertEquals(" c?", matches.get(2).getValueString());
    }

    @Test
    public void testPrimaryQuotedChars() throws NoMatchException {
        List<Match> matches = Util.matchList("'a' 'b'", Peg.primary);
        assertEquals(2, matches.size());
        assertEquals("'a'", matches.get(0).getValueString());
        assertEquals(" 'b'", matches.get(1).getValueString());
    }
}