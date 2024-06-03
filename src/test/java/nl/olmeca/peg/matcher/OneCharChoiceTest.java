package nl.olmeca.peg.matcher;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class OneCharChoiceTest {

    @Test
    public void testOneCharChoiceNegate() {
        Matcher occ = OneCharChoice.noneOf(new GivenChar(']'));
        NoMatchException ex = assertThrowsExactly(NoMatchException.class,
                () -> occ.match("]")
        );
    }

    @Test
    public void testOneCharChoice() throws NoMatchException {
        Matcher occ = OneCharChoice.of(new GivenChar(']'));
        assertEquals("]", occ.match("]").getValueString());
    }

    @Test
    public void testOneCharChoiceCapture() throws NoMatchException {
        Matcher occ = OneCharChoice.of(new GivenChar('a').capture("test"));
        Match match = occ.match("a");
        List<Match> caps = match.getCapturedMatches();
        assertEquals(1, caps.size());
        assertEquals("a", caps.get(0).getValueString());
    }
}
