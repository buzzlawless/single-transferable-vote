import java.util.ArrayList;
import java.util.List;

public class Race {

    private int seats;
    private String title;
    private List<Candidate> candidates;
    private List<Candidate> winners;

    public Race(String title, int seats, int numCandidates) {
        this.seats = seats;
        this.title = title;
        candidates = new ArrayList<>(numCandidates);
        winners = new ArrayList<>(seats);
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public int getSeats() {
        return seats;
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
