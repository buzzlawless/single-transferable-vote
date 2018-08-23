package edu.utexas.stv.election;

import java.util.ArrayList;
import java.util.List;

public class Race {

    private int seats;
    private String position;
    private List<Candidate> candidates;
    private List<Ballot> ballots;

    public Race(String position, int seats, List<Candidate> candidates) {
        this.position = position;
        this.seats = seats;
        this.candidates = candidates;
        this.ballots = new ArrayList<>();
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

    public void addBallot(Ballot ballot) {
        ballots.add(ballot);
    }

    public String getPosition() {
        return position;
    }

}
