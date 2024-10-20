package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.Pattern;

public abstract class DelegatingParser<T> extends Parser<T> {

    protected String delegateName;

    public DelegatingParser(CompositeParser<T> suite, Pattern pattern, String delegate) {
        super(suite, pattern);
        this.delegateName = delegate;
    }
}
