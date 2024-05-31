package nl.olmeca.peg.matcher;

public class CharRange extends OneChar{

    private final char from;
    private final char to;

    public CharRange(char from, char to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        char value = source[startIndex];
        if (value >= from && value <= to)
            return new Match(source, this, startIndex, 1);
        throw new NoMatchException(startIndex);
    }

    @Override
    public String name() {
        return "Char Range";
    }

}
