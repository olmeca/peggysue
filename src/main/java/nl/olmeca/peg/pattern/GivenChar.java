package nl.olmeca.peg.pattern;

public class GivenChar extends OneChar {

    private final char refChar;

    public GivenChar(char theChar) {
        this.refChar = theChar;
    }

    @Override
    public GivenChar capture(String key) {
        super.capture(key);
        return this;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        if (isValid(source[startIndex])) {
            return new Match(source, this, startIndex, 1);
        }
        else throw new NoMatchException(startIndex);
    }

    public boolean isValid(char source) {
        return source == refChar;
    }

    @Override
    public Name name() {
        return Name.GIVEN_CHAR;
    }
}
