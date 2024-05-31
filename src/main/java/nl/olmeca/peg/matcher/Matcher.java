package nl.olmeca.peg.matcher;

import java.util.List;

public abstract class Matcher {
    public final int NO_MATCH = -1;
    protected final int MAX_MATCH = 999999;
    
    private int patternStart, patternEnd;
    private boolean captured;

    public abstract Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException;

    public Match match(String source) throws NoMatchException {
        char[] charArray = source.toCharArray();
        return match(charArray, 0, charArray.length);
    }

    public Match match(char[] source, int startIndex, int endIndex) throws NoMatchException {
        if (!matchPrecondition(source, startIndex, endIndex))
            throw new NoMatchException(startIndex);
        return doMatch(source, startIndex, endIndex);
    }

    public boolean matchPrecondition(char[] source, int startIndex, int endIndex) {
        return source.length >= startIndex + minLength();
    }

    public void setCaptured(boolean capture) {
        this.captured = capture;
    }

    public Matcher capture() {
        this.captured = true;
        return this;
    }

    public boolean isCaptured() {
        return captured;
    }
    public abstract String name();
    public abstract int minLength();
}
