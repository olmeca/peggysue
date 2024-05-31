package nl.olmeca.peg.matcher;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class SequenceTest {

    @Test
    public void testSequence1() throws NoMatchException {
        Sequence sequence = Sequence.of(
                new GivenChar('a'),
                new GivenChar('b'),
                new GivenChar('c')
        );
        Match match = sequence.match("abc");
        assertEquals(0, match.start);
        assertEquals(3, match.length);
        assertEquals("abc", match.getValueString());
    }

    @Test
    public void testSequence2() throws NoMatchException {
        Sequence sequence = Sequence.of(
                new GivenChar('a'),
                new AnyChar(),
                new GivenChar('c')
        );
        CompositeMatch match = (CompositeMatch) sequence.match("abc");
        assertEquals(0, match.start);
        assertEquals(3, match.length);
        assertEquals(3, match.getMatches().size());
        assertEquals("abc", match.getValueString());
        Match sub = match.getMatches().get(0);
        assertEquals(0, sub.start);
        sub = match.getMatches().get(1);
        assertEquals(1, sub.start);
    }

    @Test
    public void testAnyCharSuccess() throws NoMatchException {
        AnyChar anyChar = new AnyChar();
        Match match = anyChar.match("a");
        assertEquals(0, match.start);
        assertEquals(1, match.length);
    }

    @Test
    public void testGivenCharSuccess() throws NoMatchException {
        GivenChar givenChar = new GivenChar('a');
        Match match = givenChar.match("a");
        assertEquals(0, match.start);
        assertEquals(1, match.length);
    }

    @Test
    public void testGivenCharFailure() {
        NoMatchException ex = assertThrowsExactly(NoMatchException.class,
                () -> new GivenChar('b').match("a"));
        assertEquals(0, ex.getIndex());
    }

    @Test
    public void testOneOrMoreSuccess() throws NoMatchException {
        OneOrMore oom = new OneOrMore(new GivenChar('b'));
        Match match = oom.match("bbbaa");
        assertEquals(0, match.start);
        assertEquals(3, match.length);
        assertEquals("bbb", match.getValueString());
    }

    @Test
    public void testOneOrMoreSuccess2() throws NoMatchException {
        Matcher ooma = Make.oneOrMoreGivenChar('a');
        Matcher oomb = Make.oneOrMoreGivenChar('b');
        Matcher seqab = Sequence.of(ooma, oomb);
        Match match = seqab.match("aabbbc");
        assertEquals(0, match.start);
        assertEquals(5, match.length);
    }

    @Test
    public void testLiteralSuccess() throws NoMatchException {
        Matcher literal = new Literal("test");
        Match match = literal.match("test123");
        assertEquals(0, match.start);
        assertEquals(4, match.length);
        assertEquals("test", match.getValueString());
    }

    @Test
    public void testCaptures() throws NoMatchException {
        Matcher ooma = Make.oneOrMoreGivenChar('a').capture();
        Matcher oomb = Make.oneOrMoreGivenChar('b').capture();
        Matcher seqab = Sequence.of(ooma, oomb);
        Match match = seqab.match("aabbbc");
        assertEquals(0, match.start);
        assertEquals(5, match.length);
        List<Match> captures = match.getCapturedMatches();
        assertEquals(2, captures.size());
        assertEquals("aa", captures.get(0).getValueString());
        assertEquals("bbb", captures.get(1).getValueString());
    }

    @Test
    public void testFollowedByGivenCharSuccess() throws NoMatchException {
        Matcher matcher = new FollowedBy(new GivenChar('z'));
        Match match = matcher.match("z");
        assertEquals(0, match.start);
    }

    @Test
    public void testFollowedByGivenCharSuccess2() throws NoMatchException {
        Matcher fb = new FollowedBy(new GivenChar('z'));
        Matcher matcher = Sequence.of(new GivenChar('a'), fb);
        Match match = matcher.match("az");
        // Should match only the 'a'
        assertEquals(0, match.start);
        assertEquals(1, match.length);
    }

    @Test
    public void testFollowedByGivenCharNoMatch() throws NoMatchException {
        Matcher fb = new FollowedBy(new GivenChar('z'));
        Matcher matcher = Sequence.of(new GivenChar('a'), fb);
        NoMatchException ex = assertThrowsExactly(NoMatchException.class,
                () -> matcher.match("ab"));
        assertEquals(0, ex.getIndex());
    }

    @Test
    public void testNotFollowedByGivenCharSuccess2() throws NoMatchException {
        Matcher fb = Make.anyNotFollowedByGivenChar('z');
        Matcher matcher = Sequence.of(new GivenChar('a'), fb);
        Match match = matcher.match("abza");
        // Should match only the 'a'
        assertEquals(0, match.start);
        assertEquals(2, match.length);
    }

    @Test
    public void testNotFollowedByGivenCharNoMatch() throws NoMatchException {
        Matcher fb = new NotFollowedBy(new GivenChar('z'));
        Matcher matcher = Sequence.of(new GivenChar('a'), fb);
        NoMatchException ex = assertThrowsExactly(NoMatchException.class,
                () -> matcher.match("az"));
        assertEquals(0, ex.getIndex());
    }
}
