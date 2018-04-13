package edu.utexas.stv.computation;

import edu.utexas.stv.election.Candidate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static edu.utexas.stv.computation.WinChecker.getWinners;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WinCheckerTest {

    @Test
    public void getWinnersTest() {
        BigDecimal quota = new BigDecimal(100);
        BigDecimal exceedQuota = quota.add(new BigDecimal(50));
        BigDecimal belowQuota = quota.subtract(new BigDecimal(50));

        Candidate runningExceedQuota = mock(Candidate.class);
        Candidate runningEqualQuota = mock(Candidate.class);
        Candidate runningBelowQuota = mock(Candidate.class);
        Candidate notRunningExceedQuota = mock(Candidate.class);
        Candidate notRunningEqualQuota = mock(Candidate.class);
        Candidate notRunningBelowQuota = mock(Candidate.class);

        when(runningExceedQuota.getVoteTotal()).thenReturn(exceedQuota);
        when(runningEqualQuota.getVoteTotal()).thenReturn(quota);
        when(runningBelowQuota.getVoteTotal()).thenReturn(belowQuota);
        when(notRunningExceedQuota.getVoteTotal()).thenReturn(exceedQuota);
        when(notRunningEqualQuota.getVoteTotal()).thenReturn(quota);
        when(notRunningBelowQuota.getVoteTotal()).thenReturn(belowQuota);

        when(runningExceedQuota.isRunning()).thenReturn(true);
        when(runningEqualQuota.isRunning()).thenReturn(true);
        when(runningBelowQuota.isRunning()).thenReturn(true);
        when(notRunningExceedQuota.isRunning()).thenReturn(false);
        when(notRunningEqualQuota.isRunning()).thenReturn(false);
        when(notRunningBelowQuota.isRunning()).thenReturn(false);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(runningExceedQuota);
        candidates.add(runningEqualQuota);
        candidates.add(runningBelowQuota);
        candidates.add(notRunningExceedQuota);
        candidates.add(notRunningEqualQuota);
        candidates.add(notRunningBelowQuota);

        assertThat(getWinners(candidates, quota)).containsExactly(runningExceedQuota, runningEqualQuota);

        verify(runningExceedQuota, times(1)).getVoteTotal();
        verify(runningExceedQuota, times(1)).isRunning();
        verify(runningEqualQuota, times(1)).getVoteTotal();
        verify(runningEqualQuota, times(1)).isRunning();
        verify(runningBelowQuota, times(1)).getVoteTotal();
        verify(runningBelowQuota, times(1)).isRunning();

        verify(notRunningExceedQuota, times(1)).isRunning();
        verify(notRunningEqualQuota, times(1)).isRunning();
        verify(notRunningBelowQuota, times(1)).isRunning();

        verifyNoMoreInteractions(runningExceedQuota);
        verifyNoMoreInteractions(runningEqualQuota);
        verifyNoMoreInteractions(runningBelowQuota);
        verifyNoMoreInteractions(notRunningExceedQuota);
        verifyNoMoreInteractions(notRunningEqualQuota);
        verifyNoMoreInteractions(notRunningBelowQuota);

    }
}
