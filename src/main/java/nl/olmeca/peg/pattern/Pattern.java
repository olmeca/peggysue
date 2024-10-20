package nl.olmeca.peg.pattern;

import nl.olmeca.peg.parser.Peg;

import java.util.Optional;

public abstract class Pattern {
    public final int NO_MATCH = -1;
    protected final int MAX_MATCH = 999999;
    
    private int patternStart, patternEnd;
    private String captureKey;

    public enum Name {
        ANY, GIVEN_CHAR, ANY_CHAR, LITERAL, START, END, CHOICE, SEQUENCE, OPTION, CHAR_RANGE, AND, NOT, SERIES, ZERO_OR_MORE, ONE_OR_MORE, RULEREF
    }
    public abstract Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException;

    public Match match(String source) throws NoMatchException {
        return match(source.toCharArray());
    }

    public Match match(String source, Grammar grammar) throws NoMatchException {
        return match(source.toCharArray(), grammar);
    }

    public Match match(char[] source, Grammar grammar) throws NoMatchException {
        return match(source, 0, source.length, grammar);
    }

    public Match match(char[] source) throws NoMatchException {
        return match(source, 0, source.length, Peg.grammar);
    }

    public Match match(char[] source, int startIndex, int endIndex) throws NoMatchException {
        return match(source, startIndex, endIndex, Peg.grammar);
    }

    public Match match(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        if (!matchPrecondition(source, startIndex, endIndex)) {
            throw new NoMatchException(startIndex);
        }
        return doMatch(source, startIndex, endIndex, grammar);
    }

    public boolean matchPrecondition(char[] source, int startIndex, int endIndex) {
        return source.length >= startIndex + minLength();
    }

    public Pattern capture(String name) {
        this.captureKey = name;
        return this;
    }

    public boolean isCaptured() {
        return captureKey != null;
    }

    public Optional<String> getCaptureKey() {
        return Optional.ofNullable(captureKey);
    }

    public abstract Name name();
    public abstract int minLength();
}
