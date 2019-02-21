package edu.utexas.stv.computation;

import java.math.BigDecimal;

import static edu.utexas.stv.computation.ElectionCalculator.mc;

class QuotaCalculator {

    static BigDecimal calculateQuota(final int totalVotes, final int seats) {
        //Integer division is intentional and used in Droop quota formula
        return new BigDecimal((totalVotes / (seats + 1)) + 1, mc);
    }
}
