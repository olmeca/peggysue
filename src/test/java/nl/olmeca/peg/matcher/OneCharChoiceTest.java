package nl.olmeca.peg.matcher;

import org.junit.jupiter.api.Test;

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
}
