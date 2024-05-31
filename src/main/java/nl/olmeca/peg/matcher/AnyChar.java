package nl.olmeca.peg.matcher;

public class AnyChar extends OneChar {

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        return new Match(source, this, startIndex,1);
    }

    @Override
    public String name() {
        return "any character";
    }

}
