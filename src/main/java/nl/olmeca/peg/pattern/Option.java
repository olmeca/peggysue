package nl.olmeca.peg.pattern;

public class Option extends Series {

    public Option(Pattern pattern) {
        super(pattern, 0, 1);
    }

    public static Option of(Pattern pattern) {
        return new Option(pattern);
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
