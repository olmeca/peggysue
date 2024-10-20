package nl.olmeca.peg.pattern;

public class Option extends Series {

    public Option(Pattern pattern) {
        super(pattern, 0, 1);
    }

    public static Option of(Pattern pattern) {
        return new Option(pattern);
    }
    public static Option of(String literal) {
        return of(new Literal(literal));
    }
    public static Option of(char value) {
        return of(new GivenChar(value));
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
    public Pattern.Name name() {
        return Name.OPTION;
    }

    @Override
    public int minLength() {
        return 0;
    }
}
