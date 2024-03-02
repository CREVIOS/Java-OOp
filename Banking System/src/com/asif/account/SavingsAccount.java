package com.asif.account;

// SavingsAccount with specific interest rate
public class SavingsAccount extends Account {
    private final static double INTEREST_RATE = 0.025; // 2.5%

    public SavingsAccount(double balance) {
        super(balance);
    }

    @Override
    public double calculateInterest(int years) {
        return balance * Math.pow(1 + INTEREST_RATE, years);
    }
}
