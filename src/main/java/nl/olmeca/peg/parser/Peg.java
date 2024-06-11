package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.AnyChar;
import nl.olmeca.peg.pattern.CharRange;
import nl.olmeca.peg.pattern.Choice;
import nl.olmeca.peg.pattern.GivenChar;
import nl.olmeca.peg.pattern.Literal;
import nl.olmeca.peg.pattern.Pattern;
import nl.olmeca.peg.pattern.NotFollowedBy;
import nl.olmeca.peg.pattern.OneCharChoice;
import nl.olmeca.peg.pattern.OneOrMore;
import nl.olmeca.peg.pattern.Option;
import nl.olmeca.peg.pattern.Rule;
import nl.olmeca.peg.pattern.RuleRef;
import nl.olmeca.peg.pattern.Grammar;
import nl.olmeca.peg.pattern.Sequence;
import nl.olmeca.peg.pattern.Wrap;
import nl.olmeca.peg.pattern.ZeroOrMore;

import java.util.Arrays;
import java.util.List;

public class Peg {

    public static final char CHARSET_NEGATE = '^';
    public static final char CHAR_COMMENT = '#';
    public static final char CHAR_SINGLE_QUOTE = '\'';
    public static final char CHAR_DOUBLE_QUOTE = '"';
    public static final char CHAR_UNDERSCORE = '_';
    public static final char CHAR_PERIOD = '.';
    public static final char PAR_OPEN = '(';
    public static final char PAR_CLOSE = ')';

    public static final String REF_PARENTHESIZED = "parenthesized";
    public static final String REF_CHOICE = "choice";

    public static final CharRange alphaLow = new CharRange('a', 'z');
    public static final CharRange alphaCaps = new CharRange('A', 'Z');
    public static final CharRange digit = new CharRange('0', '9');
    public static final GivenChar backslash = new GivenChar('\\');
    public static final Sequence escapedChar = Sequence.of(backslash, new AnyChar());
    public static final OneCharChoice alphaChar = OneCharChoice.of(alphaLow, alphaCaps);
    public static final Pattern identifier = Sequence.of(
            alphaChar,
            ZeroOrMore.of(
                    OneCharChoice.of(
                            alphaLow, alphaCaps, digit, new GivenChar(CHAR_UNDERSCORE)
                    )
            )
    );
    public static final AnyChar anyChar = new AnyChar();
    public static final GivenChar escapeChar = backslash;
    public static Pattern lineSeparator = Sequence.of(
            new GivenChar('\n'),
            Option.of(new GivenChar('\r'))
    );
    public static final Pattern whiteSpaceChar = OneCharChoice.of(
            new GivenChar('\n'),
            new GivenChar('\r'),
            new GivenChar('\t'),
            new GivenChar(' ')
    );
    public static final Pattern optSpace = ZeroOrMore.of(whiteSpaceChar);
    public static final Pattern space = OneOrMore.of(whiteSpaceChar);

    public static final Pattern comment = Sequence.of(
            new GivenChar(CHAR_COMMENT),
            new ZeroOrMore(
                    Sequence.of(
                            new NotFollowedBy(Choice.of(lineSeparator, new NotFollowedBy(anyChar))),
                            anyChar
                    )
            ),
            Option.of(lineSeparator)
    );
    public static final Literal arrow = Literal.of("<-");
    public static final Pattern ignorable = ZeroOrMore.of(Choice.of(comment, whiteSpaceChar));
    public static final Pattern identNoArrow = Sequence.of(ignorable, identifier, new NotFollowedBy(arrow));
    public static final Pattern builtin = Sequence.of(backslash, identifier);
    // prefixOpr
    public static final Pattern modifier = Choice.of(token("&"), token("!"));
    // postfixOpr
    public static final Pattern quantifier = Choice.of(token("?"), token("*"), token("+"));
    public static final Choice stringLiteral = Choice.of(quotedString(CHAR_SINGLE_QUOTE), quotedString(CHAR_DOUBLE_QUOTE));
    public static final OneCharChoice parenthesizedChar =
            OneCharChoice.noneOf(CHAR_DOUBLE_QUOTE, CHAR_SINGLE_QUOTE, PAR_OPEN, PAR_CLOSE);
    public static final Pattern parenthesized = Sequence.of(
            new GivenChar(PAR_OPEN),
            ZeroOrMore.of(parenthesizedChar),
            ZeroOrMore.of(
                    Sequence.of(
                            Choice.of(
                                    quotedString(CHAR_SINGLE_QUOTE),
                                    quotedString(CHAR_DOUBLE_QUOTE),
                                    new RuleRef(REF_PARENTHESIZED)
                            ),
                            ZeroOrMore.of(parenthesizedChar)
                    )
            ),
            new GivenChar(PAR_CLOSE)
    );
    public static final Pattern literal = Choice.of(
            token(identifier),
            token(charset()),
            token(stringLiteral),
            token(builtin),
            token(new GivenChar(CHAR_PERIOD)),
            token(parenthesized)
    );
    public static final Pattern primary = Sequence.of(
            ignorable,
            Option.of(modifier),
            literal,
            Option.of(quantifier)
    );

    public static final Pattern sequence = OneOrMore.of(primary);
    public static final Pattern choiceSeparator = Sequence.of(ignorable, new GivenChar('/'));
    public static final Pattern choice = Sequence.of(
            sequence,
            ZeroOrMore.of(Sequence.of(choiceSeparator, new RuleRef(REF_CHOICE)))
    );

    public static final Grammar grammar = new Grammar(
            List.of(
                    new Rule(REF_PARENTHESIZED, parenthesized),
                    new Rule(REF_CHOICE, choice)
            )
    );

    public static Pattern quotedString(char quote) {
        GivenChar quoteChar = new GivenChar(quote);
        Pattern anyCharButQuote = Sequence.of(
                new NotFollowedBy(quoteChar),
                new AnyChar()
        );
        return Sequence.of(
                quoteChar,
                OneOrMore.of(
                        // Either an escaped quote or any char but the quote
                        Choice.of(Sequence.of(escapeChar, quoteChar), anyCharButQuote)
                ),
                quoteChar
        );
    }

    // charsetchar <- "\\" . / [^\]]
    public static Pattern charsetChar() {
        return new Choice(Arrays.asList(
                // Either a '\' followed by
                escapedChar,
                // or any character except the ']'
                OneCharChoice.noneOf(new GivenChar(']'))
        ));
    }

    public static Pattern charsetItem() {
        return new Choice(Arrays.asList(
                Sequence.of(
                        charsetChar(),
                        new GivenChar('-'),
                        charsetChar()
                ),
                charsetChar()
        ));
    }

    public static Pattern charset() {
        return charset(CharsetParser.CHARSET);
    }

    public static Pattern charset(String captureKey) {
        return Wrap.squareBrackets(
                Sequence.of(
                        Option.of(new GivenChar(CHARSET_NEGATE)),
                        OneOrMore.of(charsetItem())
                ).capture(captureKey)
        );
    }

    public static Pattern token(String value) {
        return token(Literal.of(value));
    }

    public static Pattern token(Pattern pattern) {
        return Sequence.of(ignorable, pattern);
    }
}
