package nl.olmeca.peg.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Match {
    private final Pattern.Name name;
    protected final int start, length;
    private final char[] source;
    private final String captureKey;

    public Match(final char[] source, Pattern pattern, int start, int length) {
        this.start = start;
        this.length = length;
        this.source = source;
        this.captureKey = pattern.getCaptureKey().orElse(null);
        this.name = pattern.name();
    }

    public String getValueString() {
        return new String(getValue());
    }

    public char[] getValue() {
        return Arrays.copyOfRange(source, start, start + length);
    }

    public List<Match> getCapturedMatches(String captureKey) {
        List<Match> matches = new ArrayList<>();
        addToCaptures(matches, captureKey);
        return matches;
    }

    public List<Match> getCapturedMatches() {
        List<Match> matches = new ArrayList<>();
        addToCaptures(matches);
        return matches;
    }

    protected void addToCaptures(List<Match> matches, String captureKey) {
        if (captureKey.equals(this.captureKey))
            matches.add(this);
    }

    protected void addToCaptures(List<Match> matches) {
        if (isCaptured())
            matches.add(this);
    }

    public String toString() {
        return "'" + getValueString() + "' at " + start + " length " + length;
    }

    public boolean isCaptured() {
        return captureKey != null;
    }

    public int getLength() {
        return length;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return start + length;
    }

    public char[] getSource() {
        return source;
    }

    public String getCaptureKey() {
        return captureKey;
    }

    public Pattern.Name patternName() {
        return name;
    }
}
