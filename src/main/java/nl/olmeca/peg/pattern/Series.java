package nl.olmeca.peg.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Series extends MetaPattern {

    public static final int MAX_COUNT = Integer.MAX_VALUE;

    private final int minCount;
    private final int maxCount;
    private final Pattern separator;

    public Series(Pattern pattern, Pattern separator, int minCount, int maxCount) {
        Objects.requireNonNull(pattern, "Series pattern must not be null");
        Objects.requireNonNull(separator, "Series separator must not be null");
        this.pattern = pattern;
        this.separator = separator;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public Series(Pattern pattern, int minCount, int maxCount) {
        this(pattern, Any.ANY, minCount, maxCount);
    }

    public Series(Pattern pattern, int count) {
        this(pattern, count, count);
    }

    public int minCount() {
        return minCount;
    }

    public int maxCount() {
        return maxCount;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        int matchLength = 0;
        int i = 0;
        List<Match> subMatches = new ArrayList<>();
        while (i < minCount()) {
            // At first iteration skip matching separator
            if (i > 0)
                separator.match(source, startIndex + matchLength, endIndex, grammar);
            Match match = pattern.match(source, startIndex + matchLength, endIndex, grammar);
            subMatches.add(match);
            matchLength += match.length;
            i++;
        }
        try {
            // Iterate until exception
            while (i < maxCount()) {
                // First match the separator
                separator.match(source, startIndex + matchLength, endIndex, grammar);
                Match match = pattern.match(source, startIndex + matchLength, endIndex, grammar);
                subMatches.add(match);
                matchLength += match.length;
                i++;
            }
        } catch (NoMatchException e) {
            // Do nothing
        }
        return new CompositeMatch(source, this, startIndex, matchLength, subMatches);
    }

    @Override
    public Name name() {
        return Name.SERIES;
    }

    @Override
    public int minLength() {
        return pattern.minLength() * minCount;
    }
}
