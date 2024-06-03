package nl.olmeca.peg.matcher;

import java.util.List;

public class CompositeMatch extends Match {

    private List<Match> matches;

    public CompositeMatch(final char[] source, Matcher matcher, int start, int length, List<Match> matches) {
        super(source, matcher, start, length);
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    protected void addToCaptures(List<Match> captures) {
        super.addToCaptures(captures);

        for (Match match : matches)
            match.addToCaptures(captures);
    }
}
