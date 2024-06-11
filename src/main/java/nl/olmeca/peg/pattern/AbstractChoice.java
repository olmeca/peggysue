package nl.olmeca.peg.pattern;

public abstract class AbstractChoice<T extends Pattern> extends Composite<T> {

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
        for (T pattern : subs) {
            try {
                return pattern.doMatch(source, startIndex, endIndex, grammar);
            } catch (NoMatchException ignored) {}
        }
        log("No choice match found");
        throw new NoMatchException(startIndex);
    }
}
