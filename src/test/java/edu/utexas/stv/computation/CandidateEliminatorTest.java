package edu.utexas.stv.computation;

import edu.utexas.stv.election.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static edu.utexas.stv.computation.CandidateEliminator.eliminateLastPlace;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CandidateEliminatorTest {

    private Candidate c1Spy;
    private Candidate c2Spy;
    private List<Candidate> candidates;

    @BeforeEach
    public void setup() {
        c1Spy = spy(new Candidate("c1"));
        c2Spy = spy(new Candidate("c2"));
        candidates = new ArrayList<>();
        candidates.add(c1Spy);
        candidates.add(c2Spy);
    }

    @Test
    public void eliminateLastPlaceNoTieTest() {
        doReturn(BigDecimal.ZERO).when(c1Spy).getVoteTotal(0);
        doReturn(BigDecimal.TEN).when(c2Spy).getVoteTotal(0);

        eliminateLastPlace(candidates, 0);

        verify(c1Spy, times(1)).isRunning();
        verify(c2Spy, times(1)).isRunning();
        verify(c1Spy, times(1)).getVoteTotal(0);
        verify(c2Spy, times(1)).getVoteTotal(0);
        verify(c1Spy, times(1)).notRunning();
        verify(c1Spy, times(1)).getVotes();
        verifyNoMoreInteractions(c1Spy);
        verifyNoMoreInteractions(c2Spy);

        assertThat(c1Spy.isRunning()).isFalse();
        assertThat(c2Spy.isRunning()).isTrue();
    }

    @Test
    public void eliminateLastPlaceTieRandomTest() {
        doReturn(BigDecimal.TEN).when(c1Spy).getVoteTotal(0);
        doReturn(BigDecimal.TEN).when(c2Spy).getVoteTotal(0);

        eliminateLastPlace(candidates, 0);

        verify(c1Spy, times(1)).isRunning();
        verify(c2Spy, times(1)).isRunning();
        verify(c1Spy, times(1)).getVoteTotal(0);
        verify(c2Spy, times(1)).getVoteTotal(0);
        verify(c1Spy, atMost(1)).notRunning();
        verify(c2Spy, atMost(1)).notRunning();
        verify(c1Spy, atMost(1)).getVotes();
        verify(c2Spy, atMost(1)).getVotes();
        verifyNoMoreInteractions(c1Spy);
        verifyNoMoreInteractions(c2Spy);

        assertThat(c1Spy.isRunning()).isNotEqualTo(c2Spy.isRunning());
    }


    @Test
    public void eliminateLastPlacePrevRoundTiebreakerTest() {
        doReturn(BigDecimal.TEN).when(c1Spy).getVoteTotal(0);
        doReturn(BigDecimal.TEN).when(c2Spy).getVoteTotal(0);
        doReturn(BigDecimal.ZERO).when(c1Spy).getVoteTotal(1);
        doReturn(BigDecimal.TEN).when(c2Spy).getVoteTotal(1);

        eliminateLastPlace(candidates, 1);

        verify(c1Spy, times(2)).isRunning();
        verify(c2Spy, times(2)).isRunning();
        verify(c1Spy, times(1)).getVoteTotal(0);
        verify(c2Spy, times(1)).getVoteTotal(0);
        verify(c1Spy, times(1)).getVoteTotal(1);
        verify(c2Spy, times(1)).getVoteTotal(1);
        verify(c1Spy, times(1)).notRunning();
        verify(c1Spy, times(1)).getVotes();
        verifyNoMoreInteractions(c1Spy);
        verifyNoMoreInteractions(c2Spy);

        assertThat(c1Spy.isRunning()).isFalse();
        assertThat(c2Spy.isRunning()).isTrue();
    }

    @Test
    public void eliminateLastPlaceNotRunningTest() {
        doReturn(false).when(c1Spy).isRunning();
        doReturn(BigDecimal.TEN).when(c2Spy).getVoteTotal(0);

        eliminateLastPlace(candidates, 0);

        verify(c1Spy, times(1)).isRunning();
        verify(c2Spy, times(1)).isRunning();
        verify(c2Spy, times(1)).getVoteTotal(0);
        verify(c2Spy, times(1)).notRunning();
        verify(c2Spy, times(1)).getVotes();
        verifyNoMoreInteractions(c1Spy);
        verifyNoMoreInteractions(c2Spy);

        assertThat(c2Spy.isRunning()).isFalse();
    }

}
