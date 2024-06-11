package nl.olmeca.peg.pattern;

public class NoMatchException extends Exception {

    private final int index;
    public NoMatchException(int index) {
        super("Mismatch at index " + index);
        this.index = index;
    }

    public NoMatchException(int index, Throwable cause) {
        this(index);
        initCause(cause);
    }

    public int getIndex() {
        return index;
    }
}
