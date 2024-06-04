package nl.olmeca.peg.matcher;

import java.util.Optional;

public abstract class Matcher {
    public final int NO_MATCH = -1;
    protected final int MAX_MATCH = 999999;
    
    private int patternStart, patternEnd;
    private String captureKey;

    public abstract Match doMatch(char[] source, int startIndex, int endIndex, Rules rules) throws NoMatchException;

    public Match match(String source) throws NoMatchException {
        return match(source.toCharArray());
    }

    public Match match(String source, Rules rules) throws NoMatchException {
        return match(source.toCharArray(), rules);
    }

    public Match match(char[] source, Rules rules) throws NoMatchException {
        return match(source, 0, source.length, rules);
    }

    public Match match(char[] source) throws NoMatchException {
        return match(source, 0, source.length, new Rules());
    }

    public Match match(char[] source, int startIndex, int endIndex) throws NoMatchException {
        return match(source, startIndex, endIndex, new Rules());
    }

    public Match match(char[] source, int startIndex, int endIndex, Rules rules) throws NoMatchException {
        if (!matchPrecondition(source, startIndex, endIndex))
            throw new NoMatchException(startIndex);
        return doMatch(source, startIndex, endIndex, rules);
    }

    public boolean matchPrecondition(char[] source, int startIndex, int endIndex) {
        return source.length >= startIndex + minLength();
    }

    public Matcher capture(String name) {
        this.captureKey = name;
        return this;
    }

    public boolean isCaptured() {
        return captureKey != null;
    }

    public Optional<String> getCaptureKey() {
        return Optional.ofNullable(captureKey);
    }

    public abstract String name();
    public abstract int minLength();
}
