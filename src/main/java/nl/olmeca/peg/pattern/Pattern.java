package nl.olmeca.peg.pattern;

import java.util.Optional;

public abstract class Pattern {
    public final int NO_MATCH = -1;
    protected final int MAX_MATCH = 999999;
    
    private int patternStart, patternEnd;
    private String captureKey;

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
        return match(source, 0, source.length, new Grammar());
    }

    public Match match(char[] source, int startIndex, int endIndex) throws NoMatchException {
        return match(source, startIndex, endIndex, new Grammar());
    }

    public Match match(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        if (!matchPrecondition(source, startIndex, endIndex)) {
            log("Precondition failed at index " + startIndex + " and endIndex " + endIndex);
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

    public void log(String message) {
        System.out.println(getClass().getName() + ": " + message);
    }
    public abstract String name();
    public abstract int minLength();
}
