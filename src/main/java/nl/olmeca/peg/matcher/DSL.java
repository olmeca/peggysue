package nl.olmeca.peg.matcher;

import java.util.Arrays;

public class DSL {

    public static char CHARSET_NEGATE = '^';

    public static CharRange alphaLow = new CharRange('a', 'z');
    public static CharRange alphaCaps = new CharRange('A', 'Z');
    public static CharRange digit = new CharRange('0', '9');
    public static OneCharChoice alphaChar = OneCharChoice.of(alphaLow, alphaCaps);

    public static final Matcher identifier = Sequence.of(
            alphaChar,
            ZeroOrMore.of(OneCharChoice.of(alphaLow, alphaCaps, digit, new GivenChar('_')))
    );

    // charsetchar <- "\\" . / [^\]]
    public static final Matcher charsetChar = new Choice(Arrays.asList(
            // Either a '\' followed by
            Sequence.of(new GivenChar('\\'), new AnyChar()),
            // or any character except the ']'
            OneCharChoice.noneOf(new GivenChar(']'))
    ));

    public static final Matcher charset = Wrap.squareBrackets(
            Sequence.of(
                    Optional.of(new GivenChar(CHARSET_NEGATE)),
                    OneOrMore.of(charsetChar).capture()
            )
    );
}
