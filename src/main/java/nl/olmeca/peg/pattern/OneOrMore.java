package nl.olmeca.peg.pattern;

public class OneOrMore extends Series {

    public OneOrMore(Pattern pattern) {
        super(pattern, 1, Integer.MAX_VALUE);
    }

    public static OneOrMore of(Pattern pattern) {
        return new OneOrMore(pattern);
    }

    @Override
    public String name() {
        return "One or more";
    }
}
