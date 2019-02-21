package edu.utexas.stv.election;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Race {

    private final int seats;
    private final String position;
    private final Set<Candidate> candidates;
    private final List<Ballot> ballots;

    public Race(final String position, final int seats, final Set<Candidate> candidates) {
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

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void addBallot(final Ballot ballot) {
        ballots.add(ballot);
    }

    public String getPosition() {
        return position;
    }

}
