package nl.olmeca.peg.matcher;

import java.util.ArrayList;
import java.util.List;

public class Series extends MetaMatcher {

    public static final int MAX_COUNT = Integer.MAX_VALUE;

    private final int minCount;
    private final int maxCount;

    public Series(Matcher matcher, int minCount, int maxCount) {
        this.matcher = matcher;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public Series(Matcher matcher, int count) {
        this(matcher, count, count);
    }

    public int minCount() {
        return minCount;
    }

    public int maxCount() {
        return maxCount;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        int matchLength = 0;
        int i = 0;
        List<Match> subMatches = new ArrayList<>();
        while (i < minCount()) {
            Match match = matcher.match(source, startIndex + matchLength, endIndex);
            subMatches.add(match);
            matchLength += match.length;
            i++;
        }
        try {
            // Iterate until exception
            while (i < maxCount()) {
                Match match = matcher.match(source, startIndex + matchLength, endIndex);
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
    public String name() {
        return "Series";
    }

    @Override
    public int minLength() {
        return matcher.minLength() * minCount;
    }
}
