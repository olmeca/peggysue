package nl.olmeca.peg.matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static List<Match> matchList(String value, Matcher itemMatcher) throws NoMatchException {
        return matchList(value, itemMatcher, null);
    }

    public static List<Match> matchList(String value, Matcher itemMatcher, Matcher sepMatcher)
            throws NoMatchException {
        char[] source = value.toCharArray();
        return matchList(source, 0, source.length, itemMatcher, sepMatcher);
    }

    public static List<Match> matchList(char[] source, Matcher itemMatcher) throws NoMatchException {
        return matchList(source, 0, source.length, itemMatcher, null);
    }

    public static List<Match> matchList(char[] source, int startIndex, int endIndex,
                                       Matcher itemMatcher, Matcher sepMatcher) throws NoMatchException {
        List<Match> matches = new ArrayList<>();
        int index = startIndex;
        while (index < endIndex) {
            Match match = itemMatcher.match(source, index, endIndex);
            matches.add(match);
            index += match.length;
            // Match separator to skip and ignore it
            if (index < endIndex-1 && sepMatcher != null) {
                match = sepMatcher.match(source, index, endIndex);
                index += match.length;
            }
        }
        return matches;
    }

}
