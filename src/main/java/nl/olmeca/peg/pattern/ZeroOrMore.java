package nl.olmeca.peg.pattern;

public class ZeroOrMore extends Series {
    public ZeroOrMore(Pattern pattern) {
        super(pattern, 0, Integer.MAX_VALUE);
    }

    public static ZeroOrMore of(Pattern pattern) {
        return new ZeroOrMore(pattern);
    }

    @Override
    public String name() {
        return "Zero or more";
    }
}
