import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class VoteCounter {

    private Race race;
    private BigDecimal quota;
    private int remainingSeats;
    private int rounds;
    private PriorityQueue<Candidate> haveSurplus;
    private Set<Candidate> running;

    public VoteCounter(Race race) {
        this.race = race;
        remainingSeats = race.getSeats();
        rounds = -1; //Incremented to 0 after the initial check for winners
        haveSurplus = new PriorityQueue<>();
        running = new HashSet<>(race.getCandidates());
    }

    public void countVotes() {
        calculateQuota();
        printStatus();
        if (checkForWinners()) return;
        while (remainingSeats > 0) {
            while (!haveSurplus.isEmpty()) {
                distributeSurplus();
                printStatus();
                if (checkForWinners()) return;
            }
            eliminateLastPlace();
            printStatus();
            if (checkForWinners()) return;
        }
    }

    private void calculateQuota() {
        int totalVotes = countFirstChoiceVotes();
        //Integer division is intentional and used in Droop quota formula
        quota = new BigDecimal(
                (totalVotes / (race.getSeats() + 1)) + 1
        );
        System.out.println("Quota: " + quota);
        System.out.println();
    }

    private int countFirstChoiceVotes() {
        int total = 0;
        for (Candidate c : race.getCandidates()) {
            total += c.getVoteTotal().intValue();
        }
        return total;
    }

    private boolean checkForWinners() {
        if (remainingSeats == running.size()) {
            declareAllRunningWinners();
        } else {
            for (Candidate c : race.getCandidates()) {
                if (running.contains(c)) {
                    int comparison = c.getVoteTotal().compareTo(quota);
                    if (comparison >= 0) {
                        //Candidate equaled or exceeded quota
                        declareWinner(c);
                        if (comparison == 1) {
                            //Candidate exceeded quota
                            haveSurplus.add(c);
                        }
                    }
                }
            }
        }
        pushRoundVoteTotals();
        return remainingSeats == 0;
    }

    private void declareAllRunningWinners() {
        for (Candidate c : race.getCandidates()) {
            if (running.contains(c)) {
                declareWinner(c);
            }
        }
    }

    private void declareWinner(Candidate winner) {
        race.addWinner(winner);
        running.remove(winner);
        remainingSeats--;
    }

    private void distributeSurplus() {
        Candidate hasLargestSurplus = haveSurplus.poll();
        BigDecimal totalVotes = hasLargestSurplus.getVoteTotal();
        BigDecimal surplus = totalVotes.subtract(quota);
        //Divide surplus by totalVotes, keep up to 5 digits after decimal, rounding down
        BigDecimal transferValue = surplus.divide(totalVotes, 5, RoundingMode.DOWN);
        for (Ballot b : hasLargestSurplus.getVotes()) {
            b.setValue(transferValue.multiply(b.getValue()));
            transferVotes(hasLargestSurplus, b);
        }
    }

    private List<Candidate> getLastPlace(List<Candidate> candidates, int roundsAgo) {
        BigDecimal fewestVotes = new BigDecimal(Integer.MAX_VALUE);
        List<Candidate> lastPlaceList = new ArrayList<>();
        for (Candidate c : candidates) {
            if (running.contains(c)) {
                BigDecimal candidateVoteTotal = c.getVoteTotal(roundsAgo);
                if (candidateVoteTotal.compareTo(fewestVotes) == 0) {
                    //Tie for fewest votes
                    lastPlaceList.add(c);
                } else if (candidateVoteTotal.compareTo(fewestVotes) < 0) {
                    //New fewest votes
                    fewestVotes = c.getVoteTotal();
                    lastPlaceList.clear();
                    lastPlaceList.add(c);
                }
            }
        }
        return lastPlaceList;
    }

    private void eliminateLastPlace() {
        int roundsAgo = 0;
        List<Candidate> lastPlaceList = getLastPlace(race.getCandidates(), roundsAgo);
        while (lastPlaceList.size() > 1) {
            //Tied for last
            if (roundsAgo >= rounds) {
                eliminateRandomCandidate(lastPlaceList);
                return;
            } else {
                roundsAgo++;
                lastPlaceList = getLastPlace(lastPlaceList, roundsAgo);
            }
        }
        eliminateCandidate(lastPlaceList.get(0));
    }

    private void eliminateRandomCandidate(List<Candidate> lastPlaceList) {
        int min = 0;
        int max = lastPlaceList.size() - 1;
        int randomIndex = ThreadLocalRandom.current().nextInt(min, max + 1);
        eliminateCandidate(lastPlaceList.get(randomIndex));
    }

    private void eliminateCandidate(Candidate toEliminate) {
        running.remove(toEliminate);
        for (Ballot b : toEliminate.getVotes()) {
            transferVotes(toEliminate, b);
        }
    }

    private void transferVotes(Candidate from, Ballot ballot) {
        from.subtractVotes(ballot);
        Candidate nextPreferred = getNextEligiblePreferred(ballot);
        if (nextPreferred != null) {
            nextPreferred.addVotes(ballot);
        }
    }

    private Candidate getNextEligiblePreferred(Ballot ballot) {
        Candidate nextPreferred = ballot.getNextPreferred();
        while (nextPreferred != null && !running.contains(nextPreferred)) {
            nextPreferred = ballot.getNextPreferred();
        }
        return nextPreferred;
    }

    private void pushRoundVoteTotals() {
        rounds++;
        for (Candidate c : race.getCandidates()) {
            if (running.contains(c)) {
                c.pushRoundVoteTotal();
            }
        }
    }

    private void printStatus() {
        for (Candidate c : race.getCandidates()) {
            System.out.println(c + ": " + c.getVoteTotal());
        }
        System.out.println();
    }

}
