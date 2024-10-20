package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.NoMatchException;
import nl.olmeca.peg.pattern.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatternParserTest {

    private final PatternParser parser = new PatternParser();

    @Test
    public void testParseSingleCharset() throws NoMatchException {
        Pattern pattern = parser.parse("[a-zPG]").get();
        Assertions.assertEquals("a", pattern.match("abc").getValueString());
        Assertions.assertEquals("G", pattern.match("Gab").getValueString());
    }

    @Test
    public void testParseCharsetSequence() throws NoMatchException {
        Pattern pattern = parser.parse("[a-z] [0-9]").get();
        Assertions.assertEquals("a4", pattern.match("a48").getValueString());
        Assertions.assertEquals("s5", pattern.match("s5b").getValueString());
    }
}
