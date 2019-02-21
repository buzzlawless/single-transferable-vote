package edu.utexas.stv.election;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static edu.utexas.stv.computation.ElectionCalculator.mc;

public class Candidate implements Comparable<Candidate> {

    private final String name;
    private BigDecimal voteTotal;
    private final List<BigDecimal> roundVoteTotals;
    private final List<Ballot> votes;

    public Candidate(final String name) {
        this.name = name;
        voteTotal = new BigDecimal(0, mc);
        roundVoteTotals = new ArrayList<>();
        votes = new ArrayList<>();
    }

    public BigDecimal getVoteTotal() {
        return voteTotal;
    }

    private BigDecimal getVoteTotal(final int roundsAgo) {
        if (roundsAgo >= roundVoteTotals.size()) {
            throw new IndexOutOfBoundsException();
        }
        return roundVoteTotals.get(roundVoteTotals.size() - 1 - roundsAgo);
    }

    public void addVotes(final Ballot voteToAdd) {
        votes.add(voteToAdd);
        voteTotal = voteTotal.add(voteToAdd.getValue(), mc);

    }

    public void subtractVotes(final Ballot voteToSubtract) {
        voteTotal = voteTotal.subtract(voteToSubtract.getValue(), mc);
    }


    public void pushRoundVoteTotal() {
        roundVoteTotals.add(BigDecimal.ZERO.add(voteTotal, mc));
    }

    public List<Ballot> getVotes() {
        return votes;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(final Candidate other) {
        assert roundVoteTotals.size() == other.roundVoteTotals.size();
        // Compare candidates' vote totals, referring to the previous round if tied
        for (int i = 0; i < roundVoteTotals.size(); i++) {
            final int comparison = getVoteTotal(i).compareTo(other.getVoteTotal(i));
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }

}
