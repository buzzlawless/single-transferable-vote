package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.utexas.stv.computation.ElectionCalculator.mc;

class VoteTransferer {

    static List<Candidate> getSurplusWinners(final Set<Candidate> candidates, final BigDecimal quota) {
        return candidates.stream()
                .filter(c -> c.getVoteTotal().compareTo(quota) > 0)
                .collect(Collectors.toList());
    }

    static void distributeSurplus(final PriorityQueue<Candidate> haveSurplus, final BigDecimal quota,
                                  final Set<Candidate> candidates) {
        final Candidate hasLargestSurplus = haveSurplus.poll();
        System.out.println("Distributing surplus votes for " + hasLargestSurplus.getName());
        final BigDecimal totalVotes = hasLargestSurplus.getVoteTotal();
        final BigDecimal surplus = totalVotes.subtract(quota, mc);
        final BigDecimal transferValue = surplus.divide(totalVotes, mc);
        for (final Ballot b : hasLargestSurplus.getVotes()) {
            b.setValue(transferValue.multiply(b.getValue(), mc));
            transferVotes(hasLargestSurplus, b, candidates);
        }
    }

    static void transferVotes(final Candidate from, final Ballot ballot, final Set<Candidate> candidates) {
        from.subtractVotes(ballot);
        getNextEligiblePreferred(ballot, candidates).ifPresent(next -> next.addVotes(ballot));
    }

    private static Optional<Candidate> getNextEligiblePreferred(final Ballot ballot, final Set<Candidate> candidates) {
        Optional<Candidate> nextPreferred = ballot.getNextPreferred();
        while (nextPreferred.isPresent() && !candidates.contains(nextPreferred.get())) {
            nextPreferred = ballot.getNextPreferred();
        }
        return nextPreferred;
    }

}
