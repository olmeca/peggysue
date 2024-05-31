package nl.olmeca.peg.matcher;

import java.util.Arrays;
import java.util.List;

public class OneCharChoice extends AbstractChoice<OneChar> {
    protected boolean negate = false;

    public OneCharChoice(List<OneChar> alternatives, boolean negate) {
        this.subs = alternatives;
        this.negate = negate;
    }

    public static OneCharChoice of(OneChar... choices) {
        return new OneCharChoice(Arrays.asList(choices), false);
    }

    public static OneCharChoice noneOf(OneChar... choices) {
        return new OneCharChoice(Arrays.asList(choices), true);
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        Match result = null;
        try {
            result = super.doMatch(source, startIndex, endIndex);
        } catch (NoMatchException e) {
            if (negate) {
                return new Match(source, this, startIndex, 1);
            }
        }
        if (negate) {
            throw new NoMatchException(startIndex);
        }
        return result;
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
