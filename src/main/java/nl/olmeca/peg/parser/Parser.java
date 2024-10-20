package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Match;
import nl.olmeca.peg.pattern.NoMatchException;
import nl.olmeca.peg.pattern.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public abstract class Parser<T> {
    private static final Logger logger = LogManager.getLogger(Parser.class);

    protected Pattern pattern;
    protected CompositeParser<T> suite;

    public Parser(CompositeParser<T> suite, Pattern pattern) {
        this.pattern = pattern;
        this.suite = suite;
    }

    public abstract String getCaptureKey();
    public abstract Optional<T> parseCaptured(List<Match> capturedMatches);

    public Optional<T> parse(String charset) {
        return parse(charset.toCharArray());
    }

    public Optional<T> parse(Match match) {
        return parse(match.getSource(), match.getStart(), match.getEnd());
    }

    public Optional<T> parse(char[] charArray) {
        return parse(charArray, 0, charArray.length);
    }

    public Optional<T> parse(char[] source, int start, int end) {
        logger.debug(getClass().getSimpleName() + " parsing '" + new String(source) + "' from " + start + " to " + end);
        try {
            Match match = getPattern().match(source, start, end);
            logger.debug(getClass().getSimpleName() + " matches: " + match);
            return parseCaptured(match);
        } catch (NoMatchException e) {
            return Optional.empty();
        }
    }

    public Optional<T> parseCaptured(Match match) {
        return parseCaptured(capturedMatches(match));
    }

    public List<Match> capturedMatches(Match match) {
        return match.getCapturedMatches(getCaptureKey());
    }

    public Pattern getPattern() {
        return pattern;
    }
}
