package edu.utexas.stv.election;

import java.util.ArrayList;
import java.util.List;

public class Race {

    private int seats;
    private String title;
    private List<Candidate> candidates;
    private List<Candidate> winners;
    private List<Ballot> ballots;

    public Race(String title, int seats, List<Ballot> ballots, List<Candidate> candidates) {
        this.title = title;
        this.seats = seats;
        this.ballots = ballots;
        this.candidates = candidates;
        winners = new ArrayList<>(seats);
    }

    public int getSeats() {
        return seats;
    }

    public List<Ballot> getBallots() {
        return ballots;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void addWinner(Candidate winner) {
        winners.add(winner);
    }

    public void printWinners() {
        System.out.println(title + " winners");
        for (Candidate winner : winners) {
            System.out.println(winner);
        }
        System.out.println();
    }
}
