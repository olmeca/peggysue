package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.CharRange;
import nl.olmeca.peg.pattern.CompositeMatch;
import nl.olmeca.peg.pattern.GivenChar;
import nl.olmeca.peg.pattern.Match;
import nl.olmeca.peg.pattern.Pattern;
import nl.olmeca.peg.pattern.NoMatchException;
import nl.olmeca.peg.pattern.OneChar;
import nl.olmeca.peg.pattern.OneCharChoice;
import nl.olmeca.peg.pattern.Util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class CharsetParser {

    public static final String CHARSET = "charset";
    private static final Map<Character, Character> escapableChars = Map.ofEntries(
            entry('n', '\n'),
            entry('r', '\r'),
            entry('t', '\t'),
            entry('\\', '\\'),
            entry('"', '"'),
            entry('\'', '\''),
            entry('[', '['),
            entry(']', ']')
    );

    public static Optional<Pattern> parse(String charset) {
        char[] charArray = charset.toCharArray();
        return parse(charArray, 0, charArray.length);
    }

    public static Optional<Pattern> parse(char[] source, int start, int end) {
        try {
            Match match = Peg.charset().match(source, start, end);
            return parse(match);
        } catch (NoMatchException e) {
            return Optional.empty();
        }
    }

    public static Optional<Pattern> parse(Match match) {
        CompositeMatch capture = (CompositeMatch) match.getCapturedMatches().get(0);

        boolean negate = capture.getMatches().get(0).getLength() > 0;
        char[] charset = capture.getMatches().get(1).getValue();
        try {
            List<Match> matches = Util.matchList(charset, Peg.charsetItem());
            List<OneChar> matchers = matches.stream()
                    .map(CharsetParser::parseChar)
                    .collect(Collectors.toList());
            return Optional.of(new OneCharChoice(matchers, negate));
        } catch (NoMatchException e) {
            return Optional.empty();
        }
    }

    private static OneChar parseChar(Match match) {
        switch (match.getLength()) {
            case 1 -> {
                return new GivenChar(match.getValue()[0]);
            }
            case 2 -> {
                return new GivenChar(escapableChars.get(match.getValue()[1]));
            }
            case 3 -> {
                return new CharRange(match.getValue()[0], match.getValue()[2]);
            }
            default -> throw new IllegalArgumentException("Unsupported length: " + match.getLength());
        }
    }
}
