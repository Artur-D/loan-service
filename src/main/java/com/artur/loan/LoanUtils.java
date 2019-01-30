package com.artur.loan;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public final class LoanUtils {

    private LoanUtils() {
    }

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    public static BigDecimal getPercentage(BigDecimal amount, int percent) {
        BigDecimal bdPercent = BigDecimal.valueOf(percent);
        return amount.multiply(bdPercent).divide(ONE_HUNDRED, HALF_UP);
    }
}
