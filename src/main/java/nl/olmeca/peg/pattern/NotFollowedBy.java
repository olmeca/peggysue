package nl.olmeca.peg.pattern;

public class NotFollowedBy extends MetaPattern {

    public NotFollowedBy(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        try {
            pattern.match(source, startIndex, endIndex, grammar);
        } catch (NoMatchException e) {
            return new Match(source, this, startIndex, 0);
        }
        throw new NoMatchException(startIndex);
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public int minLength() {
        return 0;
    }
}
