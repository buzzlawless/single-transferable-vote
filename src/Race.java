import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Race {

    private int seats;
    private String title;
    private List<Ballot> ballots;
    private List<Candidate> candidates;
    private List<Candidate> winners;

    public Race(int seats, String title) {
        this.seats = seats;
        this.title = title;
        ballots = new LinkedList<>();
        candidates = new LinkedList<>();
        winners = new LinkedList<>();
    }

    public void addBallot(Ballot ballot) {
        ballots.add(ballot);
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
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
