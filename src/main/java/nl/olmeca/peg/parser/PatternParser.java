package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Pattern;

import java.util.HashMap;

import static nl.olmeca.peg.parser.CompositeParser.CHARSET_PARSER;
import static nl.olmeca.peg.parser.CompositeParser.CHOICE_PARSER;
import static nl.olmeca.peg.parser.CompositeParser.SEQUENCE_PARSER;

public class PatternParser extends CompositeParser<Pattern> {

    public PatternParser() {
        super(new HashMap<>(), CHOICE_PARSER);
        initParsers();
    }

    private void initParsers() {
        parsers.put(CHOICE_PARSER, new ChoiceParser(this, SEQUENCE_PARSER));
        parsers.put(SEQUENCE_PARSER, new SequenceParser(this, PRIMARY_PARSER));
        parsers.put(PRIMARY_PARSER, new PrimaryParser(this, Peg.primary(), CHARSET_PARSER));
        parsers.put(CHARSET_PARSER, new CharsetParser(this));
    }
}
