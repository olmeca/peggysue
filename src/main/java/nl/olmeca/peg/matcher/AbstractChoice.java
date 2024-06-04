package nl.olmeca.peg.matcher;

public abstract class AbstractChoice<T extends Matcher> extends Composite<T> {

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Rules rules) throws NoMatchException {
        for (Matcher matcher: subs) {
            try {
                return matcher.doMatch(source, startIndex, endIndex, rules);
            } catch (NoMatchException ignored) {}
        }
        throw new NoMatchException(startIndex);
    }
}
