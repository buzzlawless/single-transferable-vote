import java.math.BigDecimal;
import java.util.Queue;

public class Ballot {

    //TODO validate rankings (e.g. no duplicate candidates: 1) Morgan 2) Bob 3) Morgan)
    private Queue<Candidate> ranking;
    private BigDecimal value;

    public Ballot(Queue<Candidate> ranking, Race race) {
        this.ranking = ranking;
        value = BigDecimal.ONE;
        race.addBallot(this);
    }

    public Candidate getNextPreferred() {
        return ranking.poll();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
