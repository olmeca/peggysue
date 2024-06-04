package nl.olmeca.peg.matcher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OneCharChoice extends AbstractChoice<OneChar> {
    protected boolean negate = false;

    public OneCharChoice(List<OneChar> alternatives, boolean negate) {
        this.subs = alternatives;
        this.negate = negate;
    }

    public static OneCharChoice of(OneChar... choices) {
        return new OneCharChoice(Arrays.asList(choices), false);
    }

    public static OneCharChoice of(Character... choices) {
        return new OneCharChoice(
                Arrays.stream(choices)
                        .map(GivenChar::new)
                        .collect(Collectors.toUnmodifiableList()
                ), false
        );
    }

    public static OneCharChoice noneOf(OneChar... choices) {
        return new OneCharChoice(Arrays.asList(choices), true);
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Rules rules) throws NoMatchException {
        try {
            Match match = super.doMatch(source, startIndex, endIndex, rules);
            if (!negate) return match;
        } catch (NoMatchException e) {
            if (negate) {
                return new Match(source, this, startIndex, 1);
            } else throw e;
        }
        // The try has succeeded
        throw new NoMatchException(startIndex);
    }

    @Override
    public String name() {
        return "One char choice";
    }

    @Override
    public int minLength() {
        return 1;
    }
}
