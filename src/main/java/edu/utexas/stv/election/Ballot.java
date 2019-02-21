package edu.utexas.stv.election;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Queue;

import static edu.utexas.stv.computation.ElectionCalculator.mc;

public class Ballot {

    private final Queue<Candidate> ranking;
    private BigDecimal value;

    public Ballot(final Queue<Candidate> ranking) {
        this.ranking = ranking;
        value = new BigDecimal(1, mc);
    }

    public Optional<Candidate> getNextPreferred() {
        return Optional.ofNullable(ranking.poll());
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(final BigDecimal value) {
        this.value = value;
    }

}
