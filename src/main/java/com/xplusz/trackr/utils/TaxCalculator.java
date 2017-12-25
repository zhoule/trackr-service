package com.xplusz.trackr.utils;

import java.math.BigDecimal;

public class TaxCalculator {
    private static final double THRESHOLD = 3500;

    public static double doCalculate(double salary, double personalSPF) {
        double beyond = salary - THRESHOLD - personalSPF;
        double tax = 0;
        if (beyond <= 0) {
            tax = 0;
        } else if (beyond <= 1500) {
            tax = beyond * 0.03;
        } else if (beyond <= 4500) {
            tax = beyond * 0.1 - 105;
        } else if (beyond <= 9000) {
            tax = beyond * 0.2 - 555;
        } else if (beyond <= 35000) {
            tax = beyond * 0.25 - 1005;
        } else if (beyond <= 55000) {
            tax = beyond * 0.3 - 2755;
        } else if (beyond <= 80000) {
            tax = beyond * 0.35 - 5505;
        } else {
            tax = beyond * 0.45 - 13505;
        }
        return new BigDecimal(tax).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }
}
