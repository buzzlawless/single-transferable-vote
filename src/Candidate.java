import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Candidate implements Comparable<Candidate> {

    private String name;
    private BigDecimal voteTotal;
    private List<BigDecimal> roundVoteTotals;
    private List<Ballot> votes;

    public Candidate(String name) {
        this.name = name;
        voteTotal = BigDecimal.ZERO;
        roundVoteTotals = new ArrayList<>();
        votes = new ArrayList<>();
    }

    public BigDecimal getVoteTotal() {
        return voteTotal;
    }

    public BigDecimal getVoteTotal(int roundsAgo) {
        return roundVoteTotals.get(roundVoteTotals.size() - 1 - roundsAgo);
    }

    public void addVotes(Ballot voteToAdd) {
        votes.add(voteToAdd);
        voteTotal = voteTotal.add(voteToAdd.getValue());
    }

    public void subtractVotes(Ballot voteToSubtract) {
        voteTotal = voteTotal.subtract(voteToSubtract.getValue());
    }

    public void pushRoundVoteTotal() {
        BigDecimal roundVoteTotal = BigDecimal.ZERO;
        roundVoteTotal = roundVoteTotal.add(voteTotal);
        roundVoteTotals.add(roundVoteTotal);
    }

    public List<Ballot> getVotes() {
        return votes;
    }

    @Override
    public int compareTo(Candidate other) {
        return voteTotal.compareTo(other.getVoteTotal());
    }

    @Override
    public String toString() {
        return name;
    }

}
