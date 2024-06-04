package nl.olmeca.peg.matcher;

import java.util.HashMap;
import java.util.Map;

public class Rules {
    private Map<String, Matcher> rules;
    public Rules() {
        rules = new HashMap<String, Matcher>();
    }

    public Rules(Map<String, Matcher> rules) {
        this.rules = rules;
    }

    public Matcher get(String rule) {
        return rules.get(rule);
    }
}
