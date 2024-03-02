package com.asif.account;

// SalaryAccount with its interest rate
public class SalaryAccount extends Account {
    private final static double INTEREST_RATE = 0.02; // 2%

    public SalaryAccount(double balance) {
        super(balance);
    }

    @Override
    public double calculateInterest(int years) {
        return balance * Math.pow(1 + INTEREST_RATE, years);
    }
}
