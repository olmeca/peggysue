package nl.olmeca.peg.matcher;

public class FollowedBy extends MetaMatcher {

    public FollowedBy(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex) throws NoMatchException {
        matcher.match(source, startIndex, endIndex);
        return new Match(source, this, startIndex, 0);
    }

    @Override
    public String name() {
        return "Followed By";
    }

    @Override
    public int minLength() {
        return matcher.minLength();
    }
}
