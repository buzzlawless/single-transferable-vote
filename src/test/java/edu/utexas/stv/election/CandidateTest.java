package edu.utexas.stv.election;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayDeque;

import static org.assertj.core.api.Assertions.assertThat;

public class CandidateTest {

    private Candidate c;

    @BeforeEach
    public void init() {
        c = new Candidate("test");
    }

    @Test
    public void getVoteTotalStartStateTest() {
        c.pushRoundVoteTotal();
        assertThat(c.getVoteTotal(0)).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void getVoteTotal0RoundsAgoTest() {
        c.addVotes(new Ballot(new ArrayDeque<>()));
        c.pushRoundVoteTotal();
        assertThat(c.getVoteTotal(0)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void getVoteTotal1RoundAgoTest() {
        c.pushRoundVoteTotal();
        c.addVotes(new Ballot(new ArrayDeque<>()));
        c.pushRoundVoteTotal();
        assertThat(c.getVoteTotal(1)).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void addVotesTest() {
        Ballot b1 = new Ballot(new ArrayDeque<>());
        Ballot b2 = new Ballot(new ArrayDeque<>());
        Ballot b3 = new Ballot(new ArrayDeque<>());
        c.addVotes(b1);
        c.addVotes(b2);
        c.addVotes(b3);
        assertThat(c.getVoteTotal()).isEqualTo(new BigDecimal(3));
        assertThat(c.getVotes()).containsExactly(b1, b2, b3);
    }

    @Test
    public void subtractVotesTest() {
        Ballot b = new Ballot(new ArrayDeque<>());
        c.subtractVotes(b);
        assertThat(c.getVoteTotal()).isEqualTo(new BigDecimal(-1));
    }

    @Test
    public void pushRoundVoteTotalTest() {
        c.addVotes(new Ballot(new ArrayDeque<>()));
        c.pushRoundVoteTotal();
        assertThat(c.getVoteTotal()).isEqualTo(BigDecimal.ONE);
    }

}
