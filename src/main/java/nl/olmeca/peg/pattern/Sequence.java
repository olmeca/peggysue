package nl.olmeca.peg.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sequence extends Composite<Pattern> {

    public Sequence(List<Pattern> subs) {
        this.subs = subs;
    }

    public static Pattern of(Pattern... alternatives) {
        return of(Arrays.asList(alternatives));
    }

    public static Pattern of(List<Pattern> patterns) {
        return switch (patterns.size()) {
            case 0 -> throw new IllegalArgumentException("Empty pattern list");
            case 1 -> patterns.get(0);
            default -> new Sequence(patterns);
        };
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        int matchLen = 0;
        List<Match> subMatches = new ArrayList<>();
        try {
            for (Pattern sub: subs) {
                Match subMatch = sub.match(source, startIndex+matchLen, endIndex, grammar);
                subMatches.add(subMatch);
                matchLen += subMatch.length;
            }
        } catch (NoMatchException e) {
            throw new NoMatchException(startIndex, e);
        }
        return new CompositeMatch(source, this, startIndex, matchLen, subMatches);
    }

    @Override
    public Pattern.Name name() {
        return Name.SEQUENCE;
    }

    @Override
    public int minLength() {
        return subs.stream().map(Pattern::minLength).reduce(0, Integer::sum);
    }
}
