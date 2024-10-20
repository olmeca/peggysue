package nl.olmeca.peg.pattern;

public class Make {

    public static OneOrMore oneOrMoreGivenChar(char givenChar) {
        return new OneOrMore(new GivenChar(givenChar));
    }

    public static OneOrMore oneOrMoreAnyChar() {
        return new OneOrMore(new AnyChar());
    }

    public static Option optionalGivenChar(char givenChar) {
        return new Option(new GivenChar(givenChar));
    }

    public static Option optionalAnyChar() {
        return new Option(new AnyChar());
    }

    public static Pattern anyNotFollowedBy(Pattern pattern) {
        return Sequence.of(new Not(pattern), new AnyChar());
    }
}
