package nl.olmeca.peg.pattern;

import nl.olmeca.peg.parser.Peg;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChoiceTest {

    @Test
    public void testChoiceOfQuotedStrings() throws NoMatchException {
        Match match = Peg.choice.match("'a'/'b'", Peg.grammar);
        assertEquals(7, match.getLength());
    }

    @Test
    public void testChoiceOfQuotedStrings2() throws NoMatchException {
        Match match = Peg.choice.match(" 'a' / 'b' ", Peg.grammar);
        assertEquals(11, match.getLength());
    }

    @Test
    public void testChoiceOfIdentifiers() throws NoMatchException {
        Match match = Peg.choice.match("a/b", Peg.grammar);
        assertEquals(3, match.getLength());
    }

    @Test
    public void testChoice4() throws NoMatchException {
        Match match = Peg.choice.match("a/[zb-d\\t]", Peg.grammar);
        assertEquals(10, match.getLength());
    }

    @Test
    public void testChoice5() throws NoMatchException {
        Match match = Peg.choice.match("a+ /c [zb-d\\t]*", Peg.grammar);
        assertEquals(15, match.getLength());
    }

    @Test
    public void testChoice6() throws NoMatchException {
        Match match = Peg.choice.match("a+ \\test /c [zb-d\\t]*", Peg.grammar);
        assertEquals(21, match.getLength());
    }

    @Test
    public void testChoiceWithParens() throws NoMatchException {
        Match match = Peg.choice.match("'a'/('b')", Peg.grammar);
        assertEquals(9, match.getLength());
    }

    @Test
    public void testChoiceWithParens2() throws NoMatchException {
        Match match = Peg.choice.match("'a'/('b)')", Peg.grammar);
        assertEquals(10, match.getLength());
    }

    @Test
    public void testChoiceWithParens3() throws NoMatchException {
        Match match = Peg.choice.match("('a')/'b)'", Peg.grammar);
        assertEquals(10, match.getLength());
    }

    @Test
    public void testMatchAlternative() throws NoMatchException {
        Match match = Peg.alternative.match("'a'/'b'", Peg.grammar);
        assertEquals(3, match.getLength());
    }

    @Test
    public void testMatchAlternativeQuotedItem1() throws NoMatchException {
        Match match = Peg.alternative.match("'/a'/'b'", Peg.grammar);
        assertEquals(4, match.getLength());
    }

    @Test
    public void testMatchAlternativeQuotedItem2() throws NoMatchException {
        Match match = Peg.alternative.match("'(a'/'b'", Peg.grammar);
        assertEquals(4, match.getLength());
    }

    @Test
    public void testMatchAlternativeQuotedItem3() throws NoMatchException {
        Match match = Peg.alternative.match("'a)'/'b'", Peg.grammar);
        assertEquals(4, match.getLength());
    }

    @Test
    public void testMatchAlternativeQuotedItem4() throws NoMatchException {
        Match match = Peg.alternative.match("'(a)'/'b'", Peg.grammar);
        assertEquals(5, match.getLength());
    }

    @Test
    public void testMatchAlternativeQuotedItem5() throws NoMatchException {
        Match match = Peg.alternative.match("'/a'/('b')", Peg.grammar);
        assertEquals(4, match.getLength());
    }

    @Test
    public void testMatchAlternative2() throws NoMatchException {
        Match match = Peg.alternative.match("'a' /'b'", Peg.grammar);
        assertEquals(4, match.getLength());
    }

    @Test
    public void testMatchAlternativeItemParen1() throws NoMatchException {
        Match match = Peg.alternative.match("(c) /'b'", Peg.grammar);
        assertEquals(4, match.getLength());
    }

    @Test
    public void testMatchAlternativeItemParen2() throws NoMatchException {
        Match match = Peg.alternative.match(" (c) /'b'", Peg.grammar);
        assertEquals(5, match.getLength());
    }

    @Test
    public void testMatchAlternative4() throws NoMatchException {
        Match match = Peg.alternative.match("'a'", Peg.grammar);
        assertEquals(3, match.getLength());
    }

    @Test
    public void testGetAlts() throws NoMatchException {
        List<Match> matches = Util.stream("a/b/c", Peg.alternative, Peg.altSep, Function.identity());
        assertEquals(3, matches.size());
        assertEquals("a", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
        assertEquals("c", matches.get(2).getValueString());
    }

    @Test
    public void testGetAlts2() throws NoMatchException {
        List<Match> matches = Util.stream(" a / b / c ", Peg.alternative, Peg.altSep, Function.identity());
        assertEquals(3, matches.size());
        assertEquals(" a ", matches.get(0).getValueString());
        assertEquals(" b ", matches.get(1).getValueString());
        assertEquals(" c ", matches.get(2).getValueString());
    }

    @Test
    public void testGetAlts3() throws NoMatchException {
        List<Match> matches = Util.stream(" a 'e/f' / b / c ", Peg.alternative, Peg.altSep, Function.identity());
        assertEquals(3, matches.size());
        assertEquals(" a 'e/f' ", matches.get(0).getValueString());
        assertEquals(" b ", matches.get(1).getValueString());
        assertEquals(" c ", matches.get(2).getValueString());
    }

    @Test
    public void testGetAlts4() throws NoMatchException {
        List<Match> matches = Util.stream("a (gh) /b/c", Peg.alternative, Peg.altSep, Function.identity());
        assertEquals(3, matches.size());
        assertEquals("a (gh) ", matches.get(0).getValueString());
        assertEquals("b", matches.get(1).getValueString());
        assertEquals("c", matches.get(2).getValueString());
    }
}
