package nl.olmeca.peg.matcher;

public class StartMatcher extends Matcher {
    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        return new Match(source, this, startIndex, 0);
    }

    @Override
    public boolean matchPrecondition(char[] source, int startIndex, int endIndex) {
        return startIndex == 0;
    }

    @Override
    public String name() {
        return "Start Matcher";
    }

    @Override
    public int minLength() {
        return 0;
    }
}
