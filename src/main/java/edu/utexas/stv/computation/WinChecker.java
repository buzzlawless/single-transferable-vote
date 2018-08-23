package edu.utexas.stv.computation;

import edu.utexas.stv.election.Candidate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WinChecker {

    public static List<Candidate> getWinners(List<Candidate> candidates, BigDecimal quota) {
        List<Candidate> winners = new ArrayList<>();
        for (Candidate c : candidates) {
            if (c.isRunning()) {
                System.out.println(String.format("%s has %s votes", c.getName(), c.getVoteTotal().toPlainString()));
                if (c.getVoteTotal().compareTo(quota) >= 0) {
                    System.out.println(c.getName() + " declared winner for meeting the quota");
                    winners.add(c);
                }
            }
        }
        return winners;
    }
}
