package com.asif.account;

public abstract class Account {
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
}

