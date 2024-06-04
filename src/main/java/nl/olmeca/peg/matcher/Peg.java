package nl.olmeca.peg.matcher;

import java.util.Arrays;

public class Peg {

    public static final char CHARSET_NEGATE = '^';
    public static final char CHAR_COMMENT = '#';
    public static final char CHAR_SINGLE_QUOTE = '\'';
    public static final char CHAR_DOUBLE_QUOTE = '"';
    public static final char CHAR_UNDERSCORE = '_';
    public static final char CHAR_PERIOD = '.';

    public static final CharRange alphaLow = new CharRange('a', 'z');
    public static final CharRange alphaCaps = new CharRange('A', 'Z');
    public static final CharRange digit = new CharRange('0', '9');
    public static final Sequence escapedChar = Sequence.of(new GivenChar('\\'), new AnyChar());
    public static final OneCharChoice alphaChar = OneCharChoice.of(alphaLow, alphaCaps);
    public static final Matcher identifier = Sequence.of(
            alphaChar,
            ZeroOrMore.of(
                    OneCharChoice.of(
                            alphaLow, alphaCaps, digit, new GivenChar(CHAR_UNDERSCORE)
                    )
            )
    );
    public static AnyChar anyChar = new AnyChar();
    public static Matcher lineSeparator = Sequence.of(
            new GivenChar('\n'),
            Option.of(new GivenChar('\r'))
    );
    public static final Matcher whiteSpaceChar = OneCharChoice.of(
            new GivenChar('\n'),
            new GivenChar('\r'),
            new GivenChar('\t'),
            new GivenChar(' ')
    );
    public static final Matcher optSpace = ZeroOrMore.of(whiteSpaceChar);
    public static final Matcher space = OneOrMore.of(whiteSpaceChar);

    public static final Matcher comment = Sequence.of(
            new GivenChar(CHAR_COMMENT),
            new ZeroOrMore(
                    Sequence.of(
                            new NotAt(Choice.of(lineSeparator, new NotAt(anyChar))),
                            anyChar
                    )
            ),
            Option.of(lineSeparator)
    );
    public static final Matcher stringLiteral = Sequence.of(
            Option.of(identifier),
            Choice.of(
                    Wrap.enclosed(
                            ZeroOrMore.of(OneCharChoice.noneOf(new GivenChar(CHAR_SINGLE_QUOTE))),
                            Wrap.Quotes
                    ),
                    Wrap.enclosed(
                            ZeroOrMore.of(OneCharChoice.noneOf(new GivenChar(CHAR_DOUBLE_QUOTE))),
                            Wrap.DoubleQuotes
                    )
           )
    );
    public static final Literal arrow = Literal.of("<-");
    public static final Matcher ignorable = ZeroOrMore.of(Choice.of(comment, whiteSpaceChar));
    public static final Matcher identNoArrow = Sequence.of(ignorable, identifier, new NotAt(arrow));
    // prefixOpr
    public static final Matcher modifier = Choice.of(token("&"), token("!"));
    // postfixOpr
    public static final Matcher quantifier = Choice.of(token("?"), token("*"), token("+"));
    public static final Matcher literal = Choice.of(
            token(charset()),
            token(stringLiteral)
    );
    public static final Matcher primary = Sequence.of(
            Option.of(modifier),
            literal,
            Option.of(quantifier)
    );

    // charsetchar <- "\\" . / [^\]]
    public static Matcher charsetChar() {
        return new Choice(Arrays.asList(
                // Either a '\' followed by
                escapedChar,
                // or any character except the ']'
                OneCharChoice.noneOf(new GivenChar(']'))
        ));
    }

    public static Matcher charsetItem() {
        return new Choice(Arrays.asList(
                Sequence.of(
                        charsetChar(),
                        new GivenChar('-'),
                        charsetChar()
                ),
                charsetChar()
        ));
    }

    public static Matcher charset() {
        return charset(CharsetParser.CHARSET);
    }

    public static Matcher charset(String captureKey) {
        return Wrap.squareBrackets(
                Sequence.of(
                        Option.of(new GivenChar(CHARSET_NEGATE)),
                        OneOrMore.of(charsetItem())
                ).capture(captureKey)
        );
    }

    public static Matcher token(String value) {
        return token(Literal.of(value));
    }

    public static Matcher token(Matcher matcher) {
        return Sequence.of(ignorable, matcher);
    }
}
