package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Match;
import nl.olmeca.peg.pattern.NoMatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PegTest {

    @Test
    public void testSingleQuotedTextWithoutEscapedQuotes() throws NoMatchException {
        Match match = Peg.quotedString('\'').match("'I am ok' he said.");
        Assertions.assertEquals(9, match.getLength());
    }

    @Test
    public void testSingleQuotedTextWithEscapedQuotes() throws NoMatchException {
        Match match = Peg.quotedString('\'').match("'I\\'m ok' he said.");
        Assertions.assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedEmptyText() throws NoMatchException {
        Match match = Peg.parenthesized.match("()", Peg.grammar);
        Assertions.assertEquals(2, match.getLength());
    }

    @Test
    public void testParenthesizedText() throws NoMatchException {
        Match match = Peg.parenthesized.match("(test)", Peg.grammar);
        Assertions.assertEquals(6, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted1() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t 'b' c)", Peg.grammar);
        Assertions.assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted2() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t 'b)'c)", Peg.grammar);
        Assertions.assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted3() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t 'b('c)", Peg.grammar);
        Assertions.assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted4() throws NoMatchException {
        Match match = Peg.parenthesized.match("((b))", Peg.grammar);
        Assertions.assertEquals(5, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted5() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t (b) c)", Peg.grammar);
        Assertions.assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted6() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t (b) (c) )", Peg.grammar);
        Assertions.assertEquals(12, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted7() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t ((b) (c)) )", Peg.grammar);
        Assertions.assertEquals(14, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted8() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t '((b) (c))' )", Peg.grammar);
        Assertions.assertEquals(16, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted9() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t \"((b) (c))\" )", Peg.grammar);
        Assertions.assertEquals(16, match.getLength());
    }
}
