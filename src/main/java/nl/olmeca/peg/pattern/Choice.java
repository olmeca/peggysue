package nl.olmeca.peg.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Choice extends AbstractChoice<Pattern> {

    public Choice(List<Pattern> alternatives) {
        this.subs = alternatives;
    }

    public static Pattern of(Pattern... alternatives) {
        return of(Arrays.asList(alternatives));
    }

    public static Pattern of(List<Pattern> patterns) {
        return switch (patterns.size()) {
            case 0 -> throw new IllegalArgumentException("Empty pattern list");
            case 1 -> patterns.get(0);
            default -> new Choice(patterns);
        };
    }

    public static Pattern ofChars(Character... alternatives) {
        return new Choice(Stream.of(alternatives)
                .map(GivenChar::new)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    @Override
    public Name name() {
        return Name.CHOICE;
    }

    @Override
    public int minLength() {
        return subs.stream().mapToInt(Pattern::minLength).min().orElse(0);
    }
}
