package nl.olmeca.peg.parser;

import nl.olmeca.peg.pattern.NoMatchException;
import nl.olmeca.peg.pattern.Pattern;
import nl.olmeca.peg.pattern.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public abstract class ListParser<T> extends DelegatingParser<T> {
    private static final Logger logger = LogManager.getLogger(ListParser.class);

    protected Pattern separatorPattern;

    public ListParser(CompositeParser<T> suite, Pattern pattern, Pattern separatorPattern, String itemParserName) {
        super(suite, pattern, itemParserName);
        this.separatorPattern = separatorPattern;
    }

    public ListParser(CompositeParser<T> suite, Pattern itemPattern, String itemParserName) {
        this(suite, itemPattern, null, itemParserName);
    }

    @Override
    public Optional<T> parse(char[] source, int start, int end) {
        List<T> items;
        try {
            items = parseItems(source, start, end);
            logger.debug(getClass().getSimpleName() + " parsed " + items.size() + " items: ");
        } catch (NoMatchException e) {
            return Optional.empty();
        }
        return Optional.of(createPattern(items));
    }

    public List<T> parseItems(char[] source, int start, int end) throws NoMatchException {
        List<Optional<T>> stream = Util.stream(source, start, end, pattern, separatorPattern, getItemParser()::parse);
        return stream
                .stream().map(Optional::get).toList();
    }

    public Parser<T> getItemParser() {
        return suite.getParser(delegateName);
    }

    public abstract T createPattern(List<T> patterns);
}
