package nl.olmeca.peg.matcher;

public class Optional extends Series {

    public Optional(Matcher matcher) {
        super(matcher, 0, 1);
    }

    public static Optional of(Matcher matcher) {
        return new Optional(matcher);
    }

    @Override
    public int minCount() {
        return 0;
    }

    @Override
    public int maxCount() {
        return 1;
    }

    @Override
    public String name() {
        return "Optional";
    }

    @Override
    public int minLength() {
        return 0;
    }
}
