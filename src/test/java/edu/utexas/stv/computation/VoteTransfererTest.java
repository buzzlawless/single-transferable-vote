package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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

class VoteTransfererTest {

    @Test
    void distributeSurplusTest() {
        final BigDecimal quota = new BigDecimal(50, mc);
        final BigDecimal voteTotal = new BigDecimal(75, mc);

        final Ballot ballotToTransfer = mock(Ballot.class);
        when(ballotToTransfer.getValue()).thenReturn(new BigDecimal(1, mc));
        final List<Ballot> ballots = new ArrayList<>();
        ballots.add(ballotToTransfer);
        final Candidate hasSurplus = mock(Candidate.class);
        when(hasSurplus.getVoteTotal()).thenReturn(voteTotal);
        when(hasSurplus.getVotes()).thenReturn(ballots);
        final PriorityQueue<Candidate> haveSurplus = new PriorityQueue<>();
        haveSurplus.add(hasSurplus);
        final Set<Candidate> running = new HashSet<>();
        running.add(hasSurplus);
        distributeSurplus(haveSurplus, quota, running);

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
    void transferVotesTest() {
        final Set<Candidate> running = new HashSet<>();
        final Candidate firstChoiceEliminated = mock(Candidate.class);
        final Candidate secondChoiceNotRunning = mock(Candidate.class);
        final Candidate thirdChoiceRunning = mock(Candidate.class);
        running.add(thirdChoiceRunning);
        final Queue<Candidate> ranking = new ArrayDeque<>();
        ranking.add(secondChoiceNotRunning);
        ranking.add(thirdChoiceRunning);
        final Ballot b = new Ballot(ranking);
        transferVotes(firstChoiceEliminated, b, running);

        verify(firstChoiceEliminated, times(1)).subtractVotes(b);
        verify(thirdChoiceRunning, times(1)).addVotes(b);

        verifyNoMoreInteractions(firstChoiceEliminated);
        verifyNoMoreInteractions(secondChoiceNotRunning);
        verifyNoMoreInteractions(thirdChoiceRunning);
    }

    @Test
    void getSurplusWinnersTest() {
        final BigDecimal quota = new BigDecimal(100, mc);
        final BigDecimal greaterThanQuota = quota.add(new BigDecimal(50, mc));
        final BigDecimal lessThanQuota = quota.subtract(new BigDecimal(50, mc));

        final Candidate exceedQuota = mock(Candidate.class);
        final Candidate equalQuota = mock(Candidate.class);
        final Candidate belowQuota = mock(Candidate.class);

        when(exceedQuota.getVoteTotal()).thenReturn(greaterThanQuota);
        when(equalQuota.getVoteTotal()).thenReturn(quota);
        when(belowQuota.getVoteTotal()).thenReturn(lessThanQuota);

        final Set<Candidate> candidates = new HashSet<>();
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
