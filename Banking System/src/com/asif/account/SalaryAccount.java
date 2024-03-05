package com.asif.account;

// SalaryAccount with its interest rate
public  class SalaryAccount extends Account {
    private final static double INTEREST_RATE = 0.02; // 2%

    public SalaryAccount(double balance) {

        super(balance);
    }

    @Override
    public double calculateInterest(int years) {
        return balance * Math.pow(1 + INTEREST_RATE, years) - balance;
    }

    @Override
    public void deposit(double amount) {
        // Calculate the tax on the new amount
        double tax = calculateTax(this.balance + amount) - calculateTax(this.balance);

        // Subtract the tax from the deposited amount
        double netAmount = amount - tax;

        // Add the net amount to the balance
        this.balance += netAmount;

        System.out.println("Deposited " + netAmount + " after tax deduction. New balance: " + this.balance);
    }

    private double calculateTax(double income) {
        double tax = 0;
        if (income > 1300000) { // Income above 13 lakhs
            tax += (income - 1300000) * 0.25; // 25% tax on the rest of the money
            income = 1300000;
        }
        if (income > 800000) { // Income between 8 lakhs and 13 lakhs
            tax += (income - 800000) * 0.20; // 20% tax on the next 5 lakhs
            income = 800000;
        }
        if (income > 400000) { // Income between 4 lakhs and 8 lakhs
            tax += (income - 400000) * 0.15; // 15% tax on the next 4 lakhs
            income = 400000;
        }
        if (income > 100000) { // Income between 1 lakh and 4 lakhs
            tax += (income - 100000) * 0.10; // 10% tax on the next 3 lakhs
            income = 100000;
        }
        if (income > 300000) { // Income between 3 lakhs and 1 lakh
            tax += (income - 300000) * 0.05; // 5% tax on the next 1 lakh
        }
        // No tax for income up to 3 lakhs
        return tax;
    }
}
