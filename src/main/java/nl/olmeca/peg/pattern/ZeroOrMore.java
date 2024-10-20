package nl.olmeca.peg.pattern;

public class ZeroOrMore extends Series {
    public ZeroOrMore(Pattern pattern) {
        super(pattern, 0, Integer.MAX_VALUE);
    }

    public static ZeroOrMore of(Pattern pattern) {
        return new ZeroOrMore(pattern);
    }
    public static ZeroOrMore of(char c) {
        return of(new GivenChar(c));
    }

    @Override
    public Name name() {
        return Name.ZERO_OR_MORE;
    }
}
