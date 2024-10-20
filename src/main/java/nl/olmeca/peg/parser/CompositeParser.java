package nl.olmeca.peg.parser;

import java.util.Map;
import java.util.Optional;

// TODO: rename to CompositeParser or DelegatingParser
public class CompositeParser<T> {
    public static final String CHOICE_PARSER = "CHOICE_PARSER";
    public static final String CHARSET_PARSER = "CHARSET_PARSER";
    public static final String SEQUENCE_PARSER = "SEQUENCE_PARSER";
    public static final String PRIMARY_PARSER = "PRIMARY_PARSER";

    protected final Map<String, Parser<T>> parsers;
    private final String mainParserName;

    public CompositeParser(Map<String, Parser<T>> parsers, String mainParserName) {
        this.parsers = parsers;
        this.mainParserName = mainParserName;
    }

    public Parser<T> getParser(String parserName) {
        return parsers.get(parserName);
    }

    public Optional<T> parse(String input) {
        char[] chars = input.toCharArray();
        return getParser(mainParserName).parse(chars);
    }
}
