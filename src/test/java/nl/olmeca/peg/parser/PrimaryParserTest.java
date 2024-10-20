package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.NoMatchException;
import nl.olmeca.peg.pattern.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrimaryParserTest {

    private final PatternParser mainParser = new PatternParser();
    private final PrimaryParser parser = (PrimaryParser) mainParser.getParser(CompositeParser.PRIMARY_PARSER);

    @Test
    public void testParseChoiceWithOneCharset() throws NoMatchException {
        Pattern pattern = parser.parseChoice("[a-z]");
        Assertions.assertEquals(1, pattern.match("abc").getLength());
    }

    @Test
    public void testParseChoiceBetweenTwoCharsets() throws NoMatchException {
        Pattern pattern = parser.parseChoice("[abc]/[123]");
        Assertions.assertEquals(1, pattern.match("add").getLength());
        Assertions.assertEquals(1, pattern.match("100").getLength());
    }

    @Test
    public void testParseChoiceBetweenTwoCharsetsWithSpaces() throws NoMatchException {
        Pattern pattern = parser.parseChoice(" [abc] /[123]");
        Assertions.assertEquals(1, pattern.match("add").getLength());
        Assertions.assertEquals(1, pattern.match("100").getLength());
    }

    @Test
    public void testParseChoiceBetweenTwoCharsetsWithSpaces2() throws NoMatchException {
        Pattern pattern = parser.parseChoice(" [a-d] / [123] ");
        Assertions.assertEquals(1, pattern.match("add").getLength());
        Assertions.assertEquals(1, pattern.match("100").getLength());
        Assertions.assertEquals(1, pattern.match("c00").getLength());
    }

    @Test
    public void testParseChoiceBetweenTwoCharsetsWithNegate() throws NoMatchException {
        Pattern pattern = parser.parseChoice(" [a-d] / [^123] ");
        Assertions.assertEquals(1, pattern.match("add").getLength());
        Assertions.assertEquals(1, pattern.match("000").getLength());
        Assertions.assertEquals(1, pattern.match("400").getLength());
        Assertions.assertEquals(1, pattern.match("c00").getLength());
    }
}
