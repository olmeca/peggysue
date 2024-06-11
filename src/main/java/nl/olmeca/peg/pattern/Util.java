package nl.olmeca.peg.pattern;

import java.util.ArrayList;
import java.util.List;

public class Util {

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
