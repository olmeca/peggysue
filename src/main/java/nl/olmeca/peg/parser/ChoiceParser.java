package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Choice;
import nl.olmeca.peg.pattern.Match;
import nl.olmeca.peg.pattern.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ChoiceParser extends ListParser<Pattern> {
    private static final Logger logger = LogManager.getLogger(ChoiceParser.class);

    public ChoiceParser(CompositeParser<Pattern> suite, String delegateName) {
        super(suite, Peg.alternative, Peg.altSep, delegateName);
    }

    @Override
    public Optional<Pattern> parse(char[] source, int start, int end) {
        logger.debug("Parsing choice from '{}'", new String(source, start, end));
        return super.parse(source, start, end);
    }

    @Override
    public Optional<Pattern> parseCaptured(List<Match> capturedMatches) {
        return Optional.empty();
    }

    @Override
    public String getCaptureKey() {
        return Peg.CAPKEY_CHOICE;
    }

    @Override
    public Pattern createPattern(List<Pattern> patterns) {
        return Choice.of(patterns);
    }
}
