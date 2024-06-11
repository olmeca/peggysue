package nl.olmeca.peg.pattern;

public class FollowedBy extends MetaPattern {

    public FollowedBy(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        pattern.match(source, startIndex, endIndex, grammar);
        return new Match(source, this, startIndex, 0);
    }

    @Override
    public String name() {
        return "Followed By";
    }

    @Override
    public int minLength() {
        return pattern.minLength();
    }
}
