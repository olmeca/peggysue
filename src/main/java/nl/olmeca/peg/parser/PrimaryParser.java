package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Choice;
import nl.olmeca.peg.pattern.Match;
import nl.olmeca.peg.pattern.NoMatchException;
import nl.olmeca.peg.pattern.Pattern;
import nl.olmeca.peg.pattern.Util;

import java.util.List;
import java.util.Optional;

public class PrimaryParser extends DelegatingParser<Pattern> {

    // For now:
    private CharsetParser charsetParser = new CharsetParser();

    public PrimaryParser(CompositeParser<Pattern> suite, Pattern pattern, String delegateName) {
        super(suite, pattern, delegateName);
    }

    public Pattern parseChoice(String choiceSpec) throws NoMatchException {
        List<Pattern> alternatives = Util
                .stream(choiceSpec, Peg.alternative, Peg.altSep, charsetParser::parse)
                .stream().map(Optional::get).toList();
        return alternatives.size() == 1 ? alternatives.get(0) : new Choice(alternatives);
    }

    @Override
    public String getCaptureKey() {
        return Peg.CAPKEY_PRIMARY;
    }

    @Override
    public Pattern getPattern() {
        return Peg.primary(getCaptureKey());
    }

    @Override
    public Optional<Pattern> parseCaptured(List<Match> capturedMatches) {
        return Optional.empty();
    }

}
