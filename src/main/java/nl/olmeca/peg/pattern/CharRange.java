package nl.olmeca.peg.pattern;

public class CharRange extends OneChar{

    private final char from;
    private final char to;

    public CharRange(char from, char to) {
        if ( from >= to) throw new IllegalArgumentException("Invalid character range: from >= to");
        this.from = from;
        this.to = to;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar) throws NoMatchException {
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
