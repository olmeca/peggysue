package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.AnyChar;
import nl.olmeca.peg.pattern.CharRange;
import nl.olmeca.peg.pattern.Choice;
import nl.olmeca.peg.pattern.CompositeMatch;
import nl.olmeca.peg.pattern.GivenChar;
import nl.olmeca.peg.pattern.Match;
import nl.olmeca.peg.pattern.Not;
import nl.olmeca.peg.pattern.Pattern;
import nl.olmeca.peg.pattern.NoMatchException;
import nl.olmeca.peg.pattern.Sequence;
import nl.olmeca.peg.pattern.Util;

import java.util.List;
import java.util.Optional;

public class CharsetParser extends Parser<Pattern> {

    public CharsetParser(CompositeParser<Pattern> suite) {
        super(suite, Peg.charset());
    }

    public CharsetParser() {
        this(null);
    }

    @Override
    public Optional<Pattern> parseCaptured(List<Match> capturedMatches) {
        CompositeMatch capture = (CompositeMatch) capturedMatches.get(0);

        boolean negate = capture.getMatches().get(0).getLength() > 0;
        Match charset = capture.getMatches().get(1);
        try {
            // We create a normal Choice instead of a OneCharChoice
            List<Pattern> matchers = Util.stream(charset, Peg.charsetItem, this::parseChar);
            Pattern pattern = new Choice(matchers);
            if (negate) {
                pattern = Sequence.of(new Not(pattern), new AnyChar());
            }
            return Optional.of(pattern);
            // return Optional.of(new OneCharChoice(matchers, negate));
        } catch (NoMatchException e) {
            return Optional.empty();
        }
    }

    // TODO: Below depends on match length: not good enough
    private Pattern parseChar(Match match) {
        switch (match.getLength()) {
            case 1 -> {
                return new GivenChar(match.getSource()[match.getStart()]);
            }
            case 2 -> {
                char escapedChar = match.getSource()[match.getStart()+1];
                return new GivenChar(Peg.escapableChars.get(escapedChar));
            }
            case 3 -> {
                char from = match.getSource()[match.getStart()];
                char to = match.getSource()[match.getStart()+2];
                return new CharRange(from, to);
            }
            default -> throw new IllegalArgumentException("Unsupported length: " + match.getLength());
        }
    }

    @Override
    public String getCaptureKey() {
        return Peg.CAPKEY_CHARSET;
    }
}
