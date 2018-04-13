package edu.utexas.stv.election;

import java.math.BigDecimal;
import java.util.Queue;

import static edu.utexas.stv.computation.ElectionCalculator.mc;

public class Ballot {

    private Queue<Candidate> ranking;
    private BigDecimal value;

    public Ballot(Queue<Candidate> ranking) {
        this.ranking = ranking;
        value = new BigDecimal(1, mc);
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
