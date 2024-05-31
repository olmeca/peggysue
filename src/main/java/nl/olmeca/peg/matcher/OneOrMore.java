package nl.olmeca.peg.matcher;

public class OneOrMore extends Series {

    public OneOrMore(Matcher matcher) {
        super(matcher, 1, Integer.MAX_VALUE);
    }

    public static OneOrMore of(Matcher matcher) {
        return new OneOrMore(matcher);
    }

    @Override
    public String name() {
        return "One or more";
    }
}
