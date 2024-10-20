package nl.olmeca.peg.pattern;

public class Any extends Pattern {

    public static Any ANY = new Any();

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        return new Match(source, this, startIndex, 0);
    }

    @Override
    public Name name() {
        return Name.ANY;
    }

    @Override
    public int minLength() {
        return 0;
    }
}
