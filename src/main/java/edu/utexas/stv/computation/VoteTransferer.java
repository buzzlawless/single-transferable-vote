package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static edu.utexas.stv.computation.ElectionCalculator.mc;

public class VoteTransferer {

    public static void distributeSurplus(PriorityQueue<Candidate> haveSurplus, BigDecimal quota) {
        Candidate hasLargestSurplus = haveSurplus.poll();
        BigDecimal totalVotes = hasLargestSurplus.getVoteTotal();
        BigDecimal surplus = totalVotes.subtract(quota, mc);
        BigDecimal transferValue = surplus.divide(totalVotes, mc);
        for (Ballot b : hasLargestSurplus.getVotes()) {
            b.setValue(transferValue.multiply(b.getValue(), mc));
            transferVotes(hasLargestSurplus, b);
        }
    }

    public static void transferVotes(Candidate from, Ballot ballot) {
        from.subtractVotes(ballot);
        Candidate nextPreferred = getNextEligiblePreferred(ballot);
        if (nextPreferred != null) {
            nextPreferred.addVotes(ballot);
        }
    }

    public static List<Candidate> getSurplusWinners(List<Candidate> candidates, BigDecimal quota) {
        List<Candidate> haveSurplus = new ArrayList<>();
        for (Candidate c : candidates) {
            if (c.getVoteTotal().compareTo(quota) > 0) {
                haveSurplus.add(c);
            }
        }
        return haveSurplus;
    }

    private static Candidate getNextEligiblePreferred(Ballot ballot) {
        Candidate nextPreferred = ballot.getNextPreferred();
        while (nextPreferred != null && !nextPreferred.isRunning()) {
            nextPreferred = ballot.getNextPreferred();
        }
        return nextPreferred;
    }


}
