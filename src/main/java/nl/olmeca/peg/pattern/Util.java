package nl.olmeca.peg.pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Util {
    private static final Logger logger = LogManager.getLogger(Util.class);

    public static List<Match> matchList(String value, Pattern itemPattern) throws NoMatchException {
        return matchList(value, itemPattern, null);
    }

    public static List<Match> matchList(String value, Pattern itemPattern, Pattern sepPattern)
            throws NoMatchException {
        char[] source = value.toCharArray();
        return matchList(source, 0, source.length, itemPattern, sepPattern);
    }

    public static List<Match> matchList(char[] source, Pattern itemPattern) throws NoMatchException {
        return matchList(source, 0, source.length, itemPattern, null);
    }

    public static <T> List<T> stream(String value, Pattern itemPattern,
                                     Function<Match, T> function) throws NoMatchException {
        char[] source = value.toCharArray();
        return stream(source, 0, source.length, itemPattern, null, function);
    }

    public static <T> List<T> stream(String value, Pattern itemPattern, Pattern sepPattern,
                                     Function<Match, T> function) throws NoMatchException {
        char[] source = value.toCharArray();
        return stream(source, 0, source.length, itemPattern, sepPattern, function);
    }

    public static <T> List<T> stream(char[] source, Pattern itemPattern, Function<Match, T> function)
            throws NoMatchException {
        return stream(source, itemPattern, null, function);
    }

    public static <T> List<T> stream(char[] source, Pattern itemPattern, Pattern sepPattern,
                                     Function<Match, T> function) throws NoMatchException {
        return stream(source, 0, source.length, itemPattern, sepPattern, function);
    }

    public static <T> List<T> stream(Match match, Pattern itemPattern,
                                     Function<Match, T> function) throws NoMatchException {
        return stream(match.getSource(), match.getStart(), match.getEnd(), itemPattern, Any.ANY, function);
    }

    public static <T> List<T> stream(Match match, Pattern itemPattern,
                                     Pattern sepPattern, Function<Match, T> function) throws NoMatchException {
        return stream(match.getSource(), match.getStart(), match.getEnd(), itemPattern, sepPattern, function);
    }

    public static <T> List<T> stream(char[] source, int start, int end,
                                     Pattern itemPattern, Pattern sepPattern, Function<Match, T> function)
            throws NoMatchException {
        logger.debug("Stream start: {}, end: {}", start, end);
        List<T> result = new ArrayList<>();
        int index = start;
        while (index < end) {
            Match match = itemPattern.match(source, index, end);
            logger.debug(" Item Match: {}", match);
            result.add(function.apply(match));
            index += match.length;
            // Match separator to skip and ignore it
            if (sepPattern != null && index < end - 1) {
                match = sepPattern.match(source, index, end);
                logger.debug("Separator Match: {}", match);
                index += match.length;
            }
        }
        return result;
    }

    public static List<Match> matchList(char[] source, int startIndex, int endIndex,
                                        Pattern itemPattern, Pattern sepPattern) throws NoMatchException {
        List<Match> matches = new ArrayList<>();
        int index = startIndex;
        while (index < endIndex) {
            Match match = itemPattern.match(source, index, endIndex);
            matches.add(match);
            index += match.length;
            // Match separator to skip and ignore it
            if (index < endIndex-1 && sepPattern != null) {
                match = sepPattern.match(source, index, endIndex);
                index += match.length;
            }
        }
        return matches;
    }

}
