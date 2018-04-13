package edu.utexas.stv.computation;

import edu.utexas.stv.election.Candidate;
import edu.utexas.stv.election.Race;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static edu.utexas.stv.computation.CandidateEliminator.eliminateLastPlace;
import static edu.utexas.stv.computation.QuotaCalculator.calculateQuota;
import static edu.utexas.stv.computation.VoteCounter.countFirstChoiceVotes;
import static edu.utexas.stv.computation.VoteTransferer.distributeSurplus;
import static edu.utexas.stv.computation.VoteTransferer.getSurplusWinners;
import static edu.utexas.stv.computation.WinChecker.getWinners;

public class ElectionCalculator {

    private Race race;
    private BigDecimal quota;
    private int remainingSeats;
    private int runningCandidates;
    private int rounds;
    private List<Candidate> winners;
    private PriorityQueue<Candidate> haveSurplus;

    public static MathContext mc = new MathContext(5, RoundingMode.DOWN);

    public ElectionCalculator(Race race) {
        this.race = race;
        this.remainingSeats = race.getSeats();
        this.runningCandidates = race.getCandidates().size();
        rounds = -1;
        winners = new ArrayList<>(race.getSeats());
        haveSurplus = new PriorityQueue<>();
    }

    public List<Candidate> calculateWinners() {
        int totalVotes = countFirstChoiceVotes(race.getBallots());
        quota = calculateQuota(totalVotes, race.getSeats());
        checkForWinners();
        while (remainingSeats > 0) {
            while (!haveSurplus.isEmpty()) {
                distributeSurplus(haveSurplus, quota);
                checkForWinners();
            }
            eliminateLastPlace(race.getCandidates(), rounds);
            runningCandidates--;
            if (remainingSeats == runningCandidates) {
                declareRunningAsWinners();
                return winners;
            }
            checkForWinners();
        }
        return winners;
    }

    private void checkForWinners() {
        List<Candidate> newWinners = getWinners(race.getCandidates(), quota);
        remainingSeats -= newWinners.size();
        runningCandidates -= newWinners.size();
        winners.addAll(newWinners);
        for (Candidate c : newWinners) {
            c.notRunning();
        }
        haveSurplus.addAll(getSurplusWinners(newWinners, quota));
        pushRoundVoteTotals();
    }

    private void declareRunningAsWinners() {
        for (Candidate c : race.getCandidates()) {
            if (c.isRunning()) {
                winners.add(c);
            }
        }
    }

    private void pushRoundVoteTotals() {
        rounds++;
        for (Candidate c : race.getCandidates()) {
            if (c.isRunning()) {
                c.pushRoundVoteTotal();
            }
        }
    }

}
