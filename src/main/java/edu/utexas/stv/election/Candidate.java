package edu.utexas.stv.election;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static edu.utexas.stv.computation.ElectionCalculator.mc;

public class Candidate implements Comparable<Candidate> {

    private String name;
    private boolean running;
    private BigDecimal voteTotal;
    private List<BigDecimal> roundVoteTotals;
    private List<Ballot> votes;

    public Candidate(String name) {
        this.name = name;
        running = true;
        voteTotal = new BigDecimal(0, mc);
        roundVoteTotals = new ArrayList<>();
        votes = new ArrayList<>();
    }

    public boolean isRunning() {
        return running;
    }

    public void notRunning() {
        running = false;
    }

    public BigDecimal getVoteTotal() {
        return voteTotal;
    }

    public BigDecimal getVoteTotal(int roundsAgo) {
        return roundVoteTotals.get(roundVoteTotals.size() - 1 - roundsAgo);
    }

    public void addVotes(Ballot voteToAdd) {
        votes.add(voteToAdd);
        voteTotal = voteTotal.add(voteToAdd.getValue(), mc);

    }

    public void subtractVotes(Ballot voteToSubtract) {
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
    public int compareTo(Candidate other) {
        return voteTotal.compareTo(other.getVoteTotal());
    }

}
