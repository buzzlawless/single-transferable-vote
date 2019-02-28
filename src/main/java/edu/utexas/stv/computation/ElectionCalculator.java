package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;
import edu.utexas.stv.election.Race;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import static edu.utexas.stv.computation.CandidateEliminator.eliminateLastPlace;
import static edu.utexas.stv.computation.QuotaCalculator.calculateQuota;
import static edu.utexas.stv.computation.VoteTransferer.distributeSurplus;
import static edu.utexas.stv.computation.VoteTransferer.getSurplusWinners;
import static edu.utexas.stv.computation.WinChecker.getWinners;

public class ElectionCalculator {

    private final Race race;
    private final BigDecimal quota;
    private final Set<Candidate> winners;
    private final PriorityQueue<Candidate> haveSurplus;

    public static MathContext mc = new MathContext(5, RoundingMode.DOWN);

    public ElectionCalculator(final Race race) {
        this.race = race;
        winners = new HashSet<>(race.getSeats());
        haveSurplus = new PriorityQueue<>(Collections.reverseOrder());
        quota = calculateQuota(race.getBallots().size(), race.getSeats());
    }

    public Set<Candidate> calculateWinners() {
        System.out.println("Calculating election for " + race.getPosition());
        System.out.println(String.format("%d candidates running for %d seat(s)", race.getCandidates().size(),
                race.getSeats()));
        System.out.println(race.getBallots().size() + " total ballots cast");
        System.out.println(String.format("Quota needed to win a seat is %s votes", quota.toPlainString()));
        for (final Ballot b : race.getBallots()) {
            b.getNextPreferred().ifPresent(firstPreference -> firstPreference.addVotes(b));
        }
        checkForWinners();
        while (areOpenSeats()) {
            while (!haveSurplus.isEmpty()) {
                distributeSurplus(haveSurplus, quota, race.getCandidates());
                checkForWinners();
                if (!areOpenSeats()) {
                    printWinners(winners);
                    return winners;
                }
            }
            eliminateLastPlace(race.getCandidates());
            checkForWinners();
        }
        printWinners(winners);
        return winners;
    }

    private void printWinners(final Set<Candidate> winners) {
        System.out.println("Winners: ");
        for (final Candidate winner : winners) {
            System.out.println(winner.getName());
        }
    }

    private boolean areOpenSeats() {
        final int remainingSeats = race.getSeats() - winners.size();
        if (remainingSeats == 0) {
            System.out.println("No open seats remain.");
            return false;
        }
        if (remainingSeats >= race.getCandidates().size()) {
            System.out.println("The number of remaining seats is greater than or equal to the number of remaining " +
                    "candidates. All remaining candidates declared winners.");
            winners.addAll(race.getCandidates());
            return false;
        }
        return remainingSeats > 0;
    }

    private void checkForWinners() {
        for (final Candidate c : race.getCandidates()) {
            c.pushRoundVoteTotal();
        }
        final Set<Candidate> newWinners = getWinners(race.getCandidates(), quota);
        race.getCandidates().removeAll(newWinners);
        winners.addAll(newWinners);
        haveSurplus.addAll(getSurplusWinners(newWinners, quota));
    }

}
