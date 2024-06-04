package nl.olmeca.peg.matcher;

public class RuleRef extends Matcher {
    private String ruleName;
    public RuleRef(String ruleName) {
        this.ruleName = ruleName;
    }
    public String getRuleName() {
        return ruleName;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Rules rules)
            throws NoMatchException {
        Matcher matcher = rules.get(ruleName);
        if (matcher == null) {
            throw new NoMatchException(startIndex);
        }
        return matcher.doMatch(source, startIndex, endIndex, rules);
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public int minLength() {
        return 0;
    }
}
