package edu.utexas.stv.computation;

import edu.utexas.stv.election.Candidate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static edu.utexas.stv.computation.WinChecker.getWinners;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class WinCheckerTest {

    @Test
    public void getWinnersTest() {
        final BigDecimal quota = new BigDecimal(100);
        final BigDecimal moreThanQuota = quota.add(new BigDecimal(50));
        final BigDecimal lessThanQuota = quota.subtract(new BigDecimal(50));

        final Candidate exceedQuota = mock(Candidate.class);
        final Candidate equalQuota = mock(Candidate.class);
        final Candidate belowQuota = mock(Candidate.class);

        when(exceedQuota.getVoteTotal()).thenReturn(moreThanQuota);
        when(equalQuota.getVoteTotal()).thenReturn(quota);
        when(belowQuota.getVoteTotal()).thenReturn(lessThanQuota);

        final Set<Candidate> running = new HashSet<>();
        running.add(exceedQuota);
        running.add(equalQuota);
        running.add(belowQuota);

        assertThat(getWinners(running, quota)).containsExactlyInAnyOrder(exceedQuota, equalQuota);

        verify(exceedQuota, times(2)).getVoteTotal();
        verify(exceedQuota, times(2)).getName();
        verify(equalQuota, times(2)).getVoteTotal();
        verify(equalQuota, times(2)).getName();
        verify(belowQuota, times(2)).getVoteTotal();
        verify(belowQuota, times(1)).getName();

        verifyNoMoreInteractions(exceedQuota);
        verifyNoMoreInteractions(equalQuota);
        verifyNoMoreInteractions(belowQuota);
    }
}
