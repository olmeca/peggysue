package nl.olmeca.peg.matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Match {
    protected final int start, length;
    private final char[] value;
    private final boolean captured;

    public Match(final char[] source, Matcher matcher, int start, int length) {
        this.start = start;
        this.length = length;
        this.value = Arrays.copyOfRange(source, start, start + length);
        this.captured = matcher.isCaptured();
    }

    public String getValueString() {
        return new String(value);
    }

    public char[] getValue() {
        return value;
    }

    public List<Match> getCapturedMatches() {
        List<Match> matches = new ArrayList<>();
        addToCaptures(matches);
        return matches;
    }

    protected void addToCaptures(List<Match> matches) {
        if (isCaptured())
            matches.add(this);
    }

    public boolean isCaptured() {
        return captured;
    }
}
