package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Match;
import nl.olmeca.peg.pattern.NoMatchException;
import nl.olmeca.peg.pattern.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class PegTest {

    @Test
    public void testSingleQuotedTextWithoutEscapedQuotes() throws NoMatchException {
        Match match = Peg.quotedString('\'').match("'I am ok' he said.");
        assertEquals(9, match.getLength());
    }

    @Test
    public void testSingleQuotedTextWithEscapedQuotes() throws NoMatchException {
        Match match = Peg.quotedString('\'').match("'I\\'m ok' he said.");
        assertEquals(9, match.getLength());
    }

    @Test
    public void testOneLevelContent() throws NoMatchException {
        String source = "a b c";
        assertEquals(source, Peg.oneLevelContent.match(source).getValueString());
    }

    @Test
    public void testOneLevelContentWithQuotes() throws NoMatchException {
        String source = "a 'b' c";
        assertEquals(source, Peg.oneLevelContent.match(source).getValueString());
   }

    @Test
    public void testOneLevelContentWithParensAndQuotes() throws NoMatchException {
        String source = "a ('b') c";
        assertEquals(source, Peg.oneLevelContent.match(source).getValueString());
    }

    @Test
    public void testOneLevelContentWithParensAndQuotes2() throws NoMatchException {
        String source = "a '(b)' c";
        assertEquals(source, Peg.oneLevelContent.match(source).getValueString());
    }

    @Test
    public void testOneLevelContentWithParens() throws NoMatchException {
        String source = "a (b) c";
        assertEquals(source, Peg.oneLevelContent.match(source).getValueString());
    }

    @Test
    public void testOneLevelContentWithChoice() throws NoMatchException {
        String source = "a (b) / c";
        assertEquals(source, Peg.oneLevelContent.match(source).getValueString());
    }

    @Test
    public void testParenthesizedEmptyText() throws NoMatchException {
        Match match = Peg.parenthesized.match("()", Peg.grammar);
        assertEquals(2, match.getLength());
    }

    @Test
    public void testParenthesizedText() throws NoMatchException {
        Match match = Peg.parenthesized.match("(a)", Peg.grammar);
        assertEquals(3, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted1() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t 'b' c)", Peg.grammar);
        assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted2() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t 'b)'c)", Peg.grammar);
        assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted3() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t 'b('c)", Peg.grammar);
        assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted4() throws NoMatchException {
        Match match = Peg.parenthesized.match("((b))", Peg.grammar);
        assertEquals(5, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted5() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t (b) c)", Peg.grammar);
        assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted6() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t (b) (c) )", Peg.grammar);
        assertEquals(12, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted7() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t ((b) (c)) )", Peg.grammar);
        assertEquals(14, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted8() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t '((b) (c))' )", Peg.grammar);
        assertEquals(16, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithQuoted9() throws NoMatchException {
        Match match = Peg.parenthesized.match("(t \"((b) (c))\" )", Peg.grammar);
        assertEquals(16, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithChoice() throws NoMatchException {
        Match match = Peg.parenthesized.match("(a/b)", Peg.grammar);
        assertEquals(5, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithChoice2() throws NoMatchException {
        Match match = Peg.parenthesized.match("( a / b )", Peg.grammar);
        assertEquals(9, match.getLength());
    }

    @Test
    public void testParenthesizedTextWithChoice3() throws NoMatchException {
        Match match = Peg.parenthesized.match("( a / (b) )", Peg.grammar);
        assertEquals(11, match.getLength());
    }

    @Test
    public void testOneCharChoiceNegate() {
        Pattern occ = Peg.anyCharBut(']');
        NoMatchException ex = assertThrowsExactly(NoMatchException.class,
                () -> occ.match("]")
        );
    }
}
