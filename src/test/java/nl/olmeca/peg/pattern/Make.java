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

    public static Series charChoiceSeries(int from, int to, OneChar... oneChars) {
        OneCharChoice occ = OneCharChoice.of(oneChars);
        return new Series(occ, from, to);
    }

    public static Pattern anyNotFollowedByGivenChar(char givenChar) {
        return Sequence.of(new NotFollowedBy(new GivenChar(givenChar)), new AnyChar());
    }

    public static Pattern anyNotFollowedBy(Pattern pattern) {
        return Sequence.of(new NotFollowedBy(pattern), new AnyChar());
    }
}
