package edu.utexas.stv.computation;

import edu.utexas.stv.election.Candidate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

class WinChecker {

    static Set<Candidate> getWinners(final Set<Candidate> candidates, final BigDecimal quota) {
        final Set<Candidate> winners = new HashSet<>();
        for (final Candidate c : candidates) {
            System.out.println(String.format("%s has %s votes", c.getName(), c.getVoteTotal().toPlainString()));
            if (c.getVoteTotal().compareTo(quota) >= 0) {
                System.out.println(c.getName() + " declared winner for meeting the quota");
                winners.add(c);
            }
        }
        return winners;
    }
}
