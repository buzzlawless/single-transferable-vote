package edu.utexas.stv.computation;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static edu.utexas.stv.computation.QuotaCalculator.calculateQuota;
import static org.assertj.core.api.Assertions.assertThat;

class QuotaCalculatorTest {

    @Test
    void calculateQuotaMultiSeatTest() {
        assertThat(calculateQuota(1000, 8)).isEqualTo(new BigDecimal(112));
    }

    @Test
    void calculateQuotaSingleSeatTest() {
        assertThat(calculateQuota(1000, 1)).isEqualTo(new BigDecimal(501));
    }
}
