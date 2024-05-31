package nl.olmeca.peg.matcher;

public enum Wrap {
    Quotes('\'', '\''),
    DoubleQuotes('"', '"'),
    Parentheses('(', ')'),
    CurlyBraces('{', '}'),
    SquareBrackets('[', ']'),
    AngularBrackets('<', '>');

    private final char left;
    private final char right;

    Wrap(char left, char right) {
        this.left = left;
        this.right = right;
    }

    public char getLeft() {
        return left;
    }

    public char getRight() {
        return right;
    }

    public static Matcher squareBrackets(Matcher matcher) {
        return enclosed(matcher, SquareBrackets);
    }

    public static Matcher parentheses(Matcher matcher) {
        return enclosed(matcher, Parentheses);
    }

    public static Matcher curlyBraces(Matcher matcher) {
        return enclosed(matcher, CurlyBraces);
    }

    public static Matcher quotes(Matcher matcher) {
        return enclosed(matcher, Quotes);
    }

    public static Matcher doubleQuotes(Matcher matcher) {
        return enclosed(matcher, DoubleQuotes);
    }

    public static Matcher enclosed(Matcher matcher, Wrap wrap) {
        return enclosed(matcher, wrap.getLeft(), wrap.getRight());
    }

    public static Matcher enclosed(Matcher matcher, char left, char right) {
        return Sequence.of(
                new GivenChar(left),
                matcher,
                new GivenChar(right)
        );
    }
}
