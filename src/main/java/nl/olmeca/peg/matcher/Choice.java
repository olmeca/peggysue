package nl.olmeca.peg.matcher;

import java.util.Arrays;
import java.util.List;

public class Choice extends AbstractChoice<Matcher> {

    public Choice(List<Matcher> alternatives) {
        this.subs = alternatives;
    }

    public static Choice of(Matcher... alternatives) {
        return new Choice(Arrays.asList(alternatives));
    }

    @Override
    public String name() {
        return "Choice";
    }

    @Override
    public int minLength() {
        return subs.stream().mapToInt(Matcher::minLength).min().orElse(0);
    }
}
