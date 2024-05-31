package nl.olmeca.peg.matcher;

import java.util.List;

public abstract class Composite<T extends Matcher>  extends Matcher {
    protected List<T> subs;
}
