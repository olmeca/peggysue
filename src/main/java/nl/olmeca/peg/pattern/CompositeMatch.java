package nl.olmeca.peg.pattern;

import java.util.List;

public class CompositeMatch extends Match {

    private List<Match> matches;

    public CompositeMatch(final char[] source, Pattern pattern, int start, int length, List<Match> matches) {
        super(source, pattern, start, length);
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

    @Override
    protected void addToCaptures(List<Match> captures, String captureKey) {
        super.addToCaptures(captures, captureKey);

        for (Match match : matches)
            match.addToCaptures(captures, captureKey);
    }
}
