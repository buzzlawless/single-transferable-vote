package edu.utexas.stv.computation;

import edu.utexas.stv.election.Candidate;
import edu.utexas.stv.election.Race;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
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
        haveSurplus = new PriorityQueue<>(Collections.reverseOrder());
    }

    public List<Candidate> calculateWinners() {
        System.out.println("Calculating election for " + race.getPosition());
        System.out.println(String.format("%d candidates running for %d seat(s)", race.getCandidates().size(),
                race.getSeats()));
        System.out.println(race.getBallots().size() + " total ballots cast");
        int totalVotes = countFirstChoiceVotes(race.getBallots());
        quota = calculateQuota(totalVotes, race.getSeats());
        checkForWinners();
        while (areOpenSeats()) {
            while (!haveSurplus.isEmpty()) {
                distributeSurplus(haveSurplus, quota);
                checkForWinners();
                if (!areOpenSeats()) {
                    printWinners(winners);
                    return winners;
                }
            }
            eliminateLastPlace(race.getCandidates(), rounds);
            runningCandidates--;
            checkForWinners();
        }
        printWinners(winners);
        return winners;
    }

    private void printWinners(List<Candidate> winners) {
        System.out.println("Winners: ");
        for (Candidate winner : winners) {
            System.out.println(winner.getName());
        }
    }

    private boolean areOpenSeats() {
        if (remainingSeats == runningCandidates) {
            System.out.println("The number of remaining seats equals the number of remaining candidates. " +
                    "All remaining candidates declared winners.");
            declareRunningAsWinners();
            return false;
        }
        return remainingSeats > 0;
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
