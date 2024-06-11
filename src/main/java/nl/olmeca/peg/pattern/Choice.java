package nl.olmeca.peg.pattern;

import java.util.Arrays;
import java.util.List;

public class Choice extends AbstractChoice<Pattern> {

    public Choice(List<Pattern> alternatives) {
        this.subs = alternatives;
    }

    public static Choice of(Pattern... alternatives) {
        return new Choice(Arrays.asList(alternatives));
    }

    @Override
    public String name() {
        return "Choice";
    }

    @Override
    public int minLength() {
        return subs.stream().mapToInt(Pattern::minLength).min().orElse(0);
    }
}
