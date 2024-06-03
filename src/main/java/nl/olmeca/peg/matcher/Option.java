package nl.olmeca.peg.matcher;

public class Option extends Series {

    public Option(Matcher matcher) {
        super(matcher, 0, 1);
    }

    public static Option of(Matcher matcher) {
        return new Option(matcher);
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
