package nl.olmeca.peg.pattern;

public class Rule {
    private String name;
    private Pattern pattern;

    public Rule(String name, Pattern pattern) {
        this.name = name;
        this.pattern = pattern;
    }
    public String getName() {
        return name;
    }
    public Pattern getPattern() {
        return pattern;
    }
}
