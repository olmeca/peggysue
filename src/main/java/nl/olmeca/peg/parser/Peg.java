package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Any;
import nl.olmeca.peg.pattern.AnyChar;
import nl.olmeca.peg.pattern.CharRange;
import nl.olmeca.peg.pattern.Choice;
import nl.olmeca.peg.pattern.And;
import nl.olmeca.peg.pattern.GivenChar;
import nl.olmeca.peg.pattern.Literal;
import nl.olmeca.peg.pattern.Pattern;
import nl.olmeca.peg.pattern.Not;
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
import java.util.Map;

import static java.util.Map.entry;

public class Peg {

    public static final char CHARSET_NEGATE = '^';
    public static final char CHAR_COMMENT = '#';
    public static final char CHAR_SINGLE_QUOTE = '\'';
    public static final char CHAR_DOUBLE_QUOTE = '"';
    public static final char CHAR_UNDERSCORE = '_';
    public static final char CHAR_PERIOD = '.';
    public static final char PAR_OPEN = '(';
    public static final char PAR_CLOSE = ')';
    public static final char ALT_SEP = '/';

    public static final String REF_PAREN_CONTENT = "parenContent";
    public static final String REF_CHOICE = "choice";

    public static final String CAPKEY_CHARSET = "charset";
    public static final String CAPKEY_PRIMARY = "primary";
    public static final String CAPKEY_CHOICE = "choice";
    public static final String CAPKEY_SEQUENCE= "sequence";

    public static final Map<Character, Character> escapableChars = Map.ofEntries(
            entry('n', '\n'),
            entry('r', '\r'),
            entry('t', '\t'),
            entry('\\', '\\'),
            entry('"', '"'),
            entry('\'', '\''),
            entry('[', '['),
            entry(']', ']')
    );


    public static final Pattern any = new Any();
    public static final Pattern altSep = new GivenChar(ALT_SEP);
    public static final Pattern singleQuote = new GivenChar(CHAR_SINGLE_QUOTE);
    public static final Pattern doubleQuote = new GivenChar(CHAR_DOUBLE_QUOTE);
    public static final Pattern underscore = new GivenChar(CHAR_UNDERSCORE);
    public static final Pattern period = new GivenChar(CHAR_PERIOD);
    public static final Pattern parenOpen = new GivenChar(PAR_OPEN);
    public static final Pattern parenClose = new GivenChar(PAR_CLOSE);
    public static final CharRange alphaLow = new CharRange('a', 'z');
    public static final CharRange alphaCaps = new CharRange('A', 'Z');
    public static final CharRange digit = new CharRange('0', '9');
    public static final GivenChar backslash = new GivenChar('\\');
    public static final Pattern escapedChar = Sequence.of(backslash, new AnyChar());
    public static final Pattern alphaChar = Choice.of(alphaLow, alphaCaps);
    public static final Pattern identifier = Sequence.of(
            alphaChar,
            ZeroOrMore.of(
                    Choice.of(
                            alphaLow, alphaCaps, digit, new GivenChar(CHAR_UNDERSCORE)
                    )
            )
    );
    public static final AnyChar anyChar = new AnyChar();
    public static final GivenChar escapeChar = backslash;
    public static Pattern lineSeparator = Sequence.of(
            Option.of(new GivenChar('\r')),
            new GivenChar('\n')
    );
    public static final char CHAR_SPACE = ' ';
    public static final Pattern whiteSpaceChar = Choice.of(
            new GivenChar('\n'),
            new GivenChar('\r'),
            new GivenChar('\t'),
            new GivenChar(CHAR_SPACE)
    );
    public static final Pattern optSpace = ZeroOrMore.of(whiteSpaceChar);
    public static final Pattern space = OneOrMore.of(whiteSpaceChar);

    public static Pattern notChar(Pattern pattern) {
        return Sequence.of(new Not(pattern), new AnyChar());
    }

    public static Pattern notChar(char ch) {
        return notChar(new GivenChar(ch));
    }

    // charsetchar <- "\\" . / [^\]]
    public static final Pattern charsetChar = new Choice(
            Arrays.asList(
                    // Either a '\' followed by
                    escapedChar,
                    // or any character except the ']'
                    notChar(']')
            )
    );

    public static final Pattern charRange = Sequence.of(
            charsetChar,
            new GivenChar('-'),
            charsetChar
    );
    public static final Pattern charsetItem = Choice.of(
            charRange,
            charsetChar
    );

    public static final Pattern comment = Sequence.of(
            new GivenChar(CHAR_COMMENT),
            new ZeroOrMore(
                    Sequence.of(
                            new Not(Choice.of(lineSeparator, new Not(anyChar))),
                            anyChar
                    )
            ),
            Option.of(lineSeparator)
    );
    public static final Literal arrow = Literal.of("<-");
    public static final Pattern ignorable = ZeroOrMore.of(Choice.of(comment, whiteSpaceChar));
    public static final Pattern identNoArrow = Sequence.of(ignorable, identifier, new Not(arrow));
    public static final Pattern builtin = Sequence.of(backslash, identifier);
    // prefixOpr
    public static final Pattern modifier = token(Choice.ofChars('&', '!'));
    // postfixOpr
    public static final Pattern quantifier = Choice.of(token("?"), token("*"), token("+"));
    public static final Pattern stringLiteral = Choice.of(quotedString(CHAR_SINGLE_QUOTE), quotedString(CHAR_DOUBLE_QUOTE));
    public static final Pattern choiceSeparator = Sequence.of(ignorable, altSep);
    public static final Pattern nonEnclosingChar =
            anyCharBut(CHAR_DOUBLE_QUOTE, CHAR_SINGLE_QUOTE, PAR_OPEN, PAR_CLOSE);
    public static final Pattern transitionChar =
            Choice.ofChars(CHAR_DOUBLE_QUOTE, CHAR_SINGLE_QUOTE, PAR_OPEN, PAR_CLOSE);
    public static final Pattern enclosedSub = Choice.of(
            quotedString(CHAR_SINGLE_QUOTE),
            quotedString(CHAR_DOUBLE_QUOTE),
            Wrap.parentheses(new RuleRef(REF_PAREN_CONTENT))
    );
//    public static final Pattern oneLevelContent = oneOrMoreSepBy(ZeroOrMore.of(nonEnclosingChar), enclosedSub);
    public static final Pattern oneLevelContent = oneOrMoreSepBy(ZeroOrMore.of(nonEnclosingChar), enclosedSub);
    public static final Pattern alternativeChar =
            anyCharBut(ALT_SEP, CHAR_DOUBLE_QUOTE, CHAR_SINGLE_QUOTE, PAR_OPEN, PAR_CLOSE);
    public static final Pattern alternativeChars = ZeroOrMore.of(alternativeChar);
    public static Pattern alternative = oneOrMoreSepBy(alternativeChars, enclosedSub);
    public static Pattern parenthesized = Wrap.parentheses(oneLevelContent);
    public static final Pattern literal = Choice.of(
            token(identifier),
            charset(),
            token(stringLiteral),
            token(builtin),
            token(new GivenChar(CHAR_PERIOD)),
            token(parenthesized)
    );

    public static final Pattern sequence = OneOrMore.of(primary());
    public static final Pattern choice = Sequence.of(
            sequence,
            ZeroOrMore.of(Sequence.of(choiceSeparator, new RuleRef(REF_CHOICE)))
    );

    public static final Grammar grammar = new Grammar(
            List.of(
                    new Rule(REF_PAREN_CONTENT, oneLevelContent),
                    new Rule(REF_CHOICE, choice)
            )
    );

    public static Pattern quotedString(char quote) {
        GivenChar quoteChar = new GivenChar(quote);
        Pattern anyCharButQuote = Sequence.of(
                new Not(quoteChar),
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

    public static Pattern and(Pattern pattern) {
        return new And(pattern);
    }

    public static Pattern not(Pattern pattern) {
        return new Not(pattern);
    }

    public static Pattern ifNot(Pattern exclusion, Pattern pattern) {
        return Sequence.of(not(exclusion), pattern);
    }

    public static Pattern anyCharBut(Character... chars) {
        return ifNot(Choice.ofChars(chars), new AnyChar());
    }

    public static Pattern oneOrMoreSepBy(Pattern pattern, Pattern sep) {
        return Sequence.of(
                pattern,
                ZeroOrMore.of(
                        Sequence.of(
                                sep,
                                pattern
                        )
                )
        );
    }

    public static Pattern zeroOrMoreSepBy(Pattern pattern, Pattern sep) {
        return oneOrMoreSepBy(ZeroOrMore.of(pattern), sep);
    }

    public static Pattern charset() {
        return charset(CAPKEY_CHARSET);
    }

    public static Pattern charset(String captureKey) {
        return token(Wrap.squareBrackets(
                Sequence.of(
                        Option.of(new GivenChar(CHARSET_NEGATE)),
                        OneOrMore.of(charsetItem)
                ).capture(captureKey)
        ));
    }

    public static Pattern primary() {
        return primary(null);
    }

    public static Pattern primary(String captureKey) {
        return Sequence.of(
                ignorable,
                Option.of(
                        Choice.of(
                                new GivenChar('&').capture(captureKey),
                                new GivenChar('!').capture(captureKey)
                        )
                ).capture(captureKey),
                literal().capture(captureKey),
                ZeroOrMore.of(CHAR_SPACE),
                Option.of(quantifier).capture(captureKey)
        );
    }

    public static Pattern literal() {
        return Choice.of(
                token(identifier),
                charset(),
                token(stringLiteral),
                token(builtin),
                token(new GivenChar(CHAR_PERIOD)),
                token(parenthesized)
        );
    }

    public static Pattern token(String value) {
        return token(Literal.of(value));
    }

    public static Pattern token(Pattern pattern) {
        return Sequence.of(ignorable, pattern);
    }
}
