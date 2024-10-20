package nl.olmeca.peg.pattern;

public class Literal extends Pattern {

    private final char[] value;

    public Literal(String value) {
        this.value = value.toCharArray();
    }

    public static Literal of(String value) {
        return new Literal(value);
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        for (int i = 0; i < value.length; i++) {
            if (source[startIndex + i] != value[i]) {
                throw new NoMatchException(startIndex);
            }
        }
        return new Match(source, this, startIndex, value.length);
    }

    @Override
    public Name name() {
        return Name.LITERAL;
    }

    @Override
    public int minLength() {
        return value.length;
    }
}
