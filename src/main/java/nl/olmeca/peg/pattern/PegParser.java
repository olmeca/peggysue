package nl.olmeca.peg.pattern;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PegParser {
    private Map<String, Function<Match, Optional<Pattern>>> parsers = new HashMap<>();
    public PegParser() {
        //parsers.put(Peg.CAPKEY_CHARSET, CharsetParser::parseCaptured);
    }
}
