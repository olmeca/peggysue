package nl.olmeca.peg.matcher;

public class EndMatcher extends Matcher {
    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        // The precondition has already done validation
        return new Match(source, this, startIndex, 0);
    }

    @Override
    public boolean matchPrecondition(char[] source, int startIndex, int endIndex) {
        return startIndex == endIndex - 1;
    }

    @Override
    public String name() {
        return "End Matcher";
    }

    @Override
    public int minLength() {
        return 0;
    }
}
