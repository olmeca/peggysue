package nl.olmeca.peg.pattern;

public class AnyChar extends OneChar {

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        return new Match(source, this, startIndex,1);
    }

    @Override
    public Name name() {
        return Name.ANY_CHAR;
    }

}
