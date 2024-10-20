package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Match;
import nl.olmeca.peg.pattern.Pattern;
import nl.olmeca.peg.pattern.Sequence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class SequenceParser extends ListParser<Pattern> {
    private static final Logger logger = LogManager.getLogger(SequenceParser.class);

    public SequenceParser(CompositeParser<Pattern> suite, String delegateName) {
        super(suite, Peg.primary(), delegateName);
    }

    @Override
    public Optional<Pattern> parse(char[] source, int start, int end) {
        logger.debug("Parsing sequence from '{}'", new String(source, start, end));
        return super.parse(source, start, end);
    }

    @Override
    public String getCaptureKey() {
        return Peg.CAPKEY_SEQUENCE;
    }

    @Override
    public Optional<Pattern> parseCaptured(List<Match> capturedMatches) {
        return Optional.empty();
    }

    @Override
    public Pattern createPattern(List<Pattern> patterns) {
        return Sequence.of(patterns);
    }
}
