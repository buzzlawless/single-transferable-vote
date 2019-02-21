package edu.utexas.stv.election;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayDeque;

import static org.assertj.core.api.Assertions.assertThat;

class CandidateTest {

    private Candidate c;

    @BeforeEach
    void init() {
        c = new Candidate("test");
    }

    @Test
    void addVotesTest() {
        final Ballot b1 = new Ballot(new ArrayDeque<>());
        final Ballot b2 = new Ballot(new ArrayDeque<>());
        final Ballot b3 = new Ballot(new ArrayDeque<>());
        c.addVotes(b1);
        c.addVotes(b2);
        c.addVotes(b3);
        assertThat(c.getVoteTotal()).isEqualTo(new BigDecimal(3));
        assertThat(c.getVotes()).containsExactly(b1, b2, b3);
    }

    @Test
    void subtractVotesTest() {
        final Ballot b = new Ballot(new ArrayDeque<>());
        c.subtractVotes(b);
        assertThat(c.getVoteTotal()).isEqualTo(new BigDecimal(-1));
    }

    @Test
    void pushRoundVoteTotalTest() {
        c.addVotes(new Ballot(new ArrayDeque<>()));
        c.pushRoundVoteTotal();
        assertThat(c.getVoteTotal()).isEqualTo(BigDecimal.ONE);
    }

}
