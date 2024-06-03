package nl.olmeca.peg.matcher;

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
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        if (isValid(source[startIndex])) {
            return new Match(source, this, startIndex, 1);
        }
        else throw new NoMatchException(startIndex);
    }

    public boolean isValid(char source) {
        return source == refChar;
    }

    @Override
    public String name() {
        return "the '" + refChar + "' character";
    }
}
