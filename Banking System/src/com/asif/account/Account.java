package com.asif.account;

import com.asif.withdrawal.*;

public abstract class Account implements DirectChequeWithdrawal, BkashWithdrawal, CreditCardWithdrawal {
    protected double balance;
    public abstract double calculateInterest(int years);

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    @Override
    public void withdrawUsingCheque(double amount) {
        // Implement cheque withdrawal logic
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew " + amount + " using cheque. New balance: " + balance);
        } else {
            System.out.println("Insufficient balance for cheque withdrawal.");
        }
    }

    @Override
    public void withdrawToBkash(double amount, String bkashNumber) {
        // Implement Bkash withdrawal logic
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Transferred " + amount + " to Bkash number " + bkashNumber + ". New balance: " + balance);
        } else {
            System.out.println("Insufficient balance for Bkash transfer.");
        }
    }

    @Override
    public void withdrawUsingCreditCard(double amount) {
        // Implement credit card withdrawal logic
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew " + amount + " using credit card. New balance: " + balance);
        } else {
            System.out.println("Insufficient balance for credit card withdrawal.");
        }
    }
}

