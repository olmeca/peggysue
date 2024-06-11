package nl.olmeca.peg.pattern;

public abstract class OneChar extends Pattern {

    @Override
    public int minLength() {
        return 1;
    }
}
