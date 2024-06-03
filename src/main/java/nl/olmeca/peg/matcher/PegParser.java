package nl.olmeca.peg.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PegParser {
    private Map<String, Function<Match, Optional<Matcher>>> parsers = new HashMap<>();
}
