package nl.olmeca.peg.matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sequence extends Composite<Matcher> {

    public Sequence(List<Matcher> subs) {
        this.subs = subs;
    }

    public static Sequence of(Matcher... items) {
        return new Sequence(Arrays.asList(items));
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Rules rules) throws NoMatchException {
        int matchLen = 0;
        List<Match> subMatches = new ArrayList<>();
        try {
            for (Matcher sub: subs) {
                Match subMatch = sub.match(source, startIndex+matchLen, endIndex, rules);
                subMatches.add(subMatch);
                matchLen += subMatch.length;
            }
        } catch (NoMatchException e) {
            throw new NoMatchException(startIndex, e);
        }
        return new CompositeMatch(source, this, startIndex, matchLen, subMatches);
    }

    @Override
    public String name() {
        return "sequence";
    }

    @Override
    public int minLength() {
        return subs.stream().map(Matcher::minLength).reduce(0, Integer::sum);
    }
}
