package nl.olmeca.peg.matcher;

public abstract class OneChar extends Matcher{

    @Override
    public int minLength() {
        return 1;
    }
}
