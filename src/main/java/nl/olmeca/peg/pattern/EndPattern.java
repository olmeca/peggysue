package nl.olmeca.peg.pattern;

public class EndPattern extends Pattern {
    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        // The precondition has already done validation
        return new Match(source, this, startIndex, 0);
    }

    @Override
    public boolean matchPrecondition(char[] source, int startIndex, int endIndex) {
        return startIndex == endIndex - 1;
    }

    @Override
    public Name name() {
        return Name.END;
    }

    @Override
    public int minLength() {
        return 0;
    }
}
