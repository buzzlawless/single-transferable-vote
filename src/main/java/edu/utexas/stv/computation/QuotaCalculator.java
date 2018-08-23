package edu.utexas.stv.computation;

import java.math.BigDecimal;

import static edu.utexas.stv.computation.ElectionCalculator.mc;

public class QuotaCalculator {

    public static BigDecimal calculateQuota(int totalVotes, int seats) {
        //Integer division is intentional and used in Droop quota formula
        BigDecimal quota = new BigDecimal((totalVotes / (seats + 1)) + 1, mc);
        System.out.println(String.format("Quota needed to win a seat is %s votes", quota.toPlainString()));
        return quota;
    }
}
