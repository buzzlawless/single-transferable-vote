package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import static edu.utexas.stv.computation.ElectionCalculator.mc;
import static edu.utexas.stv.computation.VoteTransferer.distributeSurplus;
import static edu.utexas.stv.computation.VoteTransferer.getSurplusWinners;
import static edu.utexas.stv.computation.VoteTransferer.transferVotes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class VoteTransfererTest {

    @Test
    public void distributeSurplusTest() {
        BigDecimal quota = new BigDecimal(50, mc);
        BigDecimal voteTotal = new BigDecimal(75, mc);

        Ballot ballotToTransfer = mock(Ballot.class);
        when(ballotToTransfer.getValue()).thenReturn(new BigDecimal(1, mc));
        List<Ballot> ballots = new ArrayList<>();
        ballots.add(ballotToTransfer);
        Candidate hasSurplus = mock(Candidate.class);
        when(hasSurplus.getVoteTotal()).thenReturn(voteTotal);
        when(hasSurplus.getVotes()).thenReturn(ballots);
        PriorityQueue<Candidate> haveSurplus = new PriorityQueue<>();
        haveSurplus.add(hasSurplus);
        distributeSurplus(haveSurplus, quota);

        verify(hasSurplus, times(1)).getName();
        verify(hasSurplus, times(1)).getVoteTotal();
        verify(hasSurplus, times(1)).getVotes();
        verify(ballotToTransfer, times(1)).getValue();
        //Surplus = vote total - quota = 75 - 50 = 25.  Transfer value = surplus / vote total = 25/75 = 0.33333
        verify(ballotToTransfer, times(1)).setValue(new BigDecimal(0.33333, mc));
        verify(hasSurplus, times(1)).subtractVotes(ballotToTransfer);
        verify(ballotToTransfer, times(1)).getNextPreferred();

        verifyNoMoreInteractions(ballotToTransfer);
        verifyNoMoreInteractions(hasSurplus);
    }

    @Test
    public void transferVotesTest() {
        Candidate firstChoiceEliminated = mock(Candidate.class);
        Candidate secondChoiceNotRunning = mock(Candidate.class);
        when(secondChoiceNotRunning.isRunning()).thenReturn(false);
        Candidate thirdChoiceRunning = mock(Candidate.class);
        when(thirdChoiceRunning.isRunning()).thenReturn(true);
        Queue<Candidate> ranking = new ArrayDeque<>();
        ranking.add(secondChoiceNotRunning);
        ranking.add(thirdChoiceRunning);
        Ballot b = new Ballot(ranking);
        transferVotes(firstChoiceEliminated, b);

        verify(firstChoiceEliminated, times(1)).subtractVotes(b);
        verify(secondChoiceNotRunning, times(1)).isRunning();
        verify(thirdChoiceRunning, times(1)).isRunning();
        verify(thirdChoiceRunning, times(1)).addVotes(b);

        verifyNoMoreInteractions(firstChoiceEliminated);
        verifyNoMoreInteractions(secondChoiceNotRunning);
        verifyNoMoreInteractions(thirdChoiceRunning);
    }

    @Test
    public void getSurplusWinnersTest() {
        BigDecimal quota = new BigDecimal(100, mc);
        BigDecimal greaterThanQuota = quota.add(new BigDecimal(50, mc));
        BigDecimal lessThanQuota = quota.subtract(new BigDecimal(50, mc));

        Candidate exceedQuota = mock(Candidate.class);
        Candidate equalQuota = mock(Candidate.class);
        Candidate belowQuota = mock(Candidate.class);

        when(exceedQuota.getVoteTotal()).thenReturn(greaterThanQuota);
        when(equalQuota.getVoteTotal()).thenReturn(quota);
        when(belowQuota.getVoteTotal()).thenReturn(lessThanQuota);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(exceedQuota);
        candidates.add(equalQuota);
        candidates.add(belowQuota);

        assertThat(getSurplusWinners(candidates, quota)).containsExactly(exceedQuota);

        verify(exceedQuota, times(1)).getVoteTotal();
        verify(equalQuota, times(1)).getVoteTotal();
        verify(belowQuota, times(1)).getVoteTotal();

        verifyNoMoreInteractions(exceedQuota);
        verifyNoMoreInteractions(equalQuota);
        verifyNoMoreInteractions(belowQuota);
    }
}
