package nl.olmeca.peg.pattern;

public class StartPattern extends Pattern {
    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        // TODO: test and fix
        return new Match(source, this, startIndex, 0);
    }

    @Override
    public boolean matchPrecondition(char[] source, int startIndex, int endIndex) {
        return startIndex == 0;
    }

    @Override
    public Name name() {
        return Name.START;
    }

    @Override
    public int minLength() {
        return 0;
    }
}
