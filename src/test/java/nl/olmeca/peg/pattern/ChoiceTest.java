package nl.olmeca.peg.pattern;

import nl.olmeca.peg.parser.Peg;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChoiceTest {

    @Test
    public void testChoiceOfQuotedStrings() throws NoMatchException {
        Match match = Peg.choice.match("'a'/'b'", Peg.grammar);
        Assertions.assertEquals(7, match.getLength());
    }

    @Test
    public void testChoiceOfQuotedStrings2() throws NoMatchException {
        Match match = Peg.choice.match(" 'a' / 'b' ", Peg.grammar);
        Assertions.assertEquals(10, match.getLength());
    }

    @Test
    public void testChoiceOfIdentifiers() throws NoMatchException {
        Match match = Peg.choice.match("a/b", Peg.grammar);
        Assertions.assertEquals(3, match.getLength());
    }

    @Test
    public void testChoice4() throws NoMatchException {
        Match match = Peg.choice.match("a/[zb-d\\t]", Peg.grammar);
        Assertions.assertEquals(10, match.getLength());
    }

    @Test
    public void testChoice5() throws NoMatchException {
        Match match = Peg.choice.match("a+ /c [zb-d\\t]*", Peg.grammar);
        Assertions.assertEquals(15, match.getLength());
    }

    @Test
    public void testChoice6() throws NoMatchException {
        Match match = Peg.choice.match("a+ \\test /c [zb-d\\t]*", Peg.grammar);
        Assertions.assertEquals(21, match.getLength());
    }

}
