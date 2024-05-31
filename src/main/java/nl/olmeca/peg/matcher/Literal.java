package nl.olmeca.peg.matcher;

public class Literal extends Matcher {

    private final char[] value;

    public Literal(String value) {
        this.value = value.toCharArray();
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        for (int i = 0; i < value.length; i++) {
            if (source[startIndex + i] != value[i]) {
                throw new NoMatchException(startIndex);
            }
        }
        return new Match(source, this, startIndex, value.length);
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public int minLength() {
        return value.length;
    }
}
