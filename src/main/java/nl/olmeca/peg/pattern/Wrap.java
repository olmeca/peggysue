package nl.olmeca.peg.pattern;

public enum Wrap {
    Parentheses('(', ')'),
    CurlyBraces('{', '}'),
    SquareBrackets('[', ']');

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

    public static Pattern squareBrackets(Pattern pattern) {
        return enclosed(pattern, SquareBrackets);
    }

    public static Pattern parentheses(Pattern pattern) {
        return enclosed(pattern, Parentheses);
    }

    public static Pattern curlyBraces(Pattern pattern) {
        return enclosed(pattern, CurlyBraces);
    }

    public static Pattern enclosed(Pattern pattern, Wrap wrap) {
        return enclosed(pattern, wrap.getLeft(), wrap.getRight());
    }

    public static Pattern enclosed(Pattern pattern, char left, char right) {
        return Sequence.of(
                new GivenChar(left),
                pattern,
                new GivenChar(right)
        );
    }
}
