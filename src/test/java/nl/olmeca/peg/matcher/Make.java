package nl.olmeca.peg.matcher;

public class Make {

    public static OneOrMore oneOrMoreGivenChar(char givenChar) {
        return new OneOrMore(new GivenChar(givenChar));
    }

    public static OneOrMore oneOrMoreAnyChar() {
        return new OneOrMore(new AnyChar());
    }

    public static Optional optionalGivenChar(char givenChar) {
        return new Optional(new GivenChar(givenChar));
    }

    public static Optional optionalAnyChar() {
        return new Optional(new AnyChar());
    }

    public static Series charChoiceSeries(int from, int to, OneChar... oneChars) {
        OneCharChoice occ = OneCharChoice.of(oneChars);
        return new Series(occ, from, to);
    }

    public static Matcher anyNotFollowedByGivenChar(char givenChar) {
        return Sequence.of(new NotFollowedBy(new GivenChar(givenChar)), new AnyChar());
    }

    public static Matcher anyNotFollowedBy(Matcher matcher) {
        return Sequence.of(new NotFollowedBy(matcher), new AnyChar());
    }
}
