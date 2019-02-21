package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import static edu.utexas.stv.computation.CandidateEliminator.eliminateLastPlace;
import static org.assertj.core.api.Assertions.assertThat;

class CandidateEliminatorTest {

    private Candidate c1;
    private Candidate c2;
    private Set<Candidate> candidates;

    @BeforeEach
    void setup() {
        c1 = new Candidate("c1");
        c2 = new Candidate("c2");
        candidates = new HashSet<>();
        candidates.add(c1);
        candidates.add(c2);
    }

    @Test
    void eliminateLastPlaceNoTieTest() {
        c2.addVotes(new Ballot(new ArrayDeque<>()));
        c1.pushRoundVoteTotal();
        c2.pushRoundVoteTotal();
        eliminateLastPlace(candidates);
        assertThat(candidates).containsExactly(c2);
    }

    @Test
    void eliminateLastPlaceTieRandomTest() {
        c1.pushRoundVoteTotal();
        c2.pushRoundVoteTotal();
        eliminateLastPlace(candidates);
        assertThat(candidates).hasSize(1);
    }

    @Test
    void eliminateLastPlacePrevRoundTiebreakerTest() {
        c1.addVotes(new Ballot(new ArrayDeque<>()));
        c1.pushRoundVoteTotal();
        c2.pushRoundVoteTotal();
        c2.addVotes(new Ballot(new ArrayDeque<>()));
        c1.pushRoundVoteTotal();
        c2.pushRoundVoteTotal();
        eliminateLastPlace(candidates);
        assertThat(candidates).containsExactly(c1);
    }

}
