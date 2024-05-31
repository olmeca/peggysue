package nl.olmeca.peg.matcher;

public class ZeroOrMore extends Series {
    public ZeroOrMore(Matcher matcher) {
        super(matcher, 1, Integer.MAX_VALUE);
    }

    public static ZeroOrMore of(Matcher matcher) {
        return new ZeroOrMore(matcher);
    }

    @Override
    public String name() {
        return "Zero or more";
    }
}
