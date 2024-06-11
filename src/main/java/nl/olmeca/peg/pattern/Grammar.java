package nl.olmeca.peg.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Grammar {
    private List<Rule> rules;
    public Grammar() {
        rules = new ArrayList<>();
    }

    public Grammar(List<Rule> rules) {
        this.rules = rules;
    }

    public Optional<Rule> get(String ruleName) {
        System.out.println("Get Rule name: " + ruleName);
        return rules.stream().filter(rule -> ruleName.equals(rule.getName())).findFirst();
    }
}
