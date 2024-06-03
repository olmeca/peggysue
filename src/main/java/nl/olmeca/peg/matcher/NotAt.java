package nl.olmeca.peg.matcher;

public class NotAt extends MetaMatcher {

    public NotAt(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        try {
            matcher.match(source, startIndex, endIndex);
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
