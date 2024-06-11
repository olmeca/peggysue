package nl.olmeca.peg.pattern;

import java.util.Optional;

public class RuleRef extends Pattern {
    private String ruleName;
    public RuleRef(String ruleName) {
        this.ruleName = ruleName;
    }

    @Override
    public Match doMatch(char[] source, int startIndex, int endIndex, Grammar grammar)
            throws NoMatchException {
        Optional<Rule> optRule = grammar.get(ruleName);
        if (optRule.isEmpty()) {
            log("Rule " + ruleName + " not found");
            throw new NoMatchException(startIndex);
        }
        return optRule.get().getPattern().doMatch(source, startIndex, endIndex, grammar);
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
