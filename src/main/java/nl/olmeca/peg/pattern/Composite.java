package nl.olmeca.peg.pattern;

import java.util.List;

public abstract class Composite<T extends Pattern>  extends Pattern {
    protected List<T> subs;
}
