package com.xplusz.trackr.utils;

import com.xplusz.trackr.model.SPF;

import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_HALF_DOWN;

public class SalaryCalculator {

    public static double calculateFinalSalary(double salary, double workingDays, double allowance, double tax, SPF spf, double expenses) {
        double actualSalary = salary;
        double actualAllowance = allowance;
        double finalSalary = 0;
        if (22 != workingDays) {
            actualSalary = calculateActualMonthSalary(salary, workingDays);
            actualAllowance = calculateActualMonthAllowance(salary, workingDays);
        }

        finalSalary = actualSalary - tax - spf.getPersonalSPF() + actualAllowance + expenses;

        return new BigDecimal(finalSalary).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double calculateActualMonthSalary(double salary, double workingDays) {
        return new BigDecimal(calculateDailySalary(salary, workingDays)).multiply(new BigDecimal(workingDays)).doubleValue();
    }

    public static double calculateActualMonthAllowance(double allowance, double workingDays) {
        return new BigDecimal(calculateDailySalary(allowance, workingDays)).multiply(new BigDecimal(workingDays)).doubleValue();
    }

    public static double calculateDailySalary(double allowance, double workingDays) {
        return new BigDecimal(allowance).divide(new BigDecimal(workingDays), 2, ROUND_HALF_DOWN).doubleValue();
    }

    public static double calculateDailyAllowance(double baseSalary, double workingDays) {
        return new BigDecimal(baseSalary).divide(new BigDecimal(workingDays), 2, ROUND_HALF_DOWN).doubleValue();
    }

//    public static void main(String... args) {
//        System.out.println("20000/22 = " + calculateActualMonthSalary(20000, 22));
//        System.out.println("400/22 = " + calculateActualMonthAllowance(400, 22));
//
//    }
}
