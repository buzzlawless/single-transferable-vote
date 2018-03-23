package edu.utexas.stv.election;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandidateTest {

    private Candidate c;

    @BeforeEach
    public void init() {
        c = new Candidate("test");
    }

    @Test
    public void getVoteTotalStartStateTest() {
        c.pushRoundVoteTotal();
        assertEquals(BigDecimal.ZERO, c.getVoteTotal(0));
    }

    @Test
    public void getVoteTotal0RoundsAgoTest() {
        c.addVotes(new Ballot(new ArrayDeque<>()));
        c.pushRoundVoteTotal();
        assertEquals(BigDecimal.ONE, c.getVoteTotal(0));
    }

    @Test
    public void getVoteTotal1RoundAgoTest() {
        c.pushRoundVoteTotal();
        c.addVotes(new Ballot(new ArrayDeque<>()));
        c.pushRoundVoteTotal();
        assertEquals(BigDecimal.ZERO, c.getVoteTotal(1));
    }

    @Test
    public void addVotesTest() {
        Ballot b = new Ballot(new ArrayDeque<>());
        c.addVotes(b);
        assertEquals(BigDecimal.ONE, c.getVoteTotal());
        assertEquals(1, c.getVotes().size());
        assertEquals(b, c.getVotes().get(0));
    }

    @Test
    public void subtractVotesTest() {
        Ballot b = new Ballot(new ArrayDeque<>());
        c.subtractVotes(b);
        assertEquals(new BigDecimal(-1), c.getVoteTotal());
    }

    @Test
    public void pushRoundVoteTotalTest() {
        c.addVotes(new Ballot(new ArrayDeque<>()));
        c.pushRoundVoteTotal();
        assertEquals(BigDecimal.ONE, c.getVoteTotal());
    }

}
