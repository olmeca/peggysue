package nl.olmeca.peg.pattern;

public class And extends MetaPattern {

    public And(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        pattern.match(source, startIndex, endIndex, grammar);
        return new Match(source, this, startIndex, 0);
    }

    @Override
    public Name name() {
        return Name.AND;
    }

    @Override
    public int minLength() {
        return pattern.minLength();
    }
}
