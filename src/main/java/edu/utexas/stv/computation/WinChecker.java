package edu.utexas.stv.computation;

import edu.utexas.stv.election.Candidate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WinChecker {

    public static List<Candidate> getWinners(List<Candidate> candidates, BigDecimal quota) {
        List<Candidate> winners = new ArrayList<>();
        for (Candidate c : candidates) {
            if (c.isRunning() && c.getVoteTotal().compareTo(quota) >= 0) {
                winners.add(c);
            }
        }
        return winners;
    }

}
