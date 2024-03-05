package com.asif.client;

import com.asif.account.Account;
import com.asif.bank.Bank;

public class Customer extends Client{

    protected String account_number;
    protected Account account; // Link to Account

    public Customer(String name, String email, String phoneNumber, Bank bank) {
        super(name, email, phoneNumber, bank);
        this.account_number = null;
    }

    public String getAccountNumber() {
        return this.account_number;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccountNumber(String uniqueAccountNumber) {
        this.account_number = uniqueAccountNumber;
    }


    public static class SinglePerson extends Customer{

        String Tin_number;
        public SinglePerson(String name, String email, String phoneNumber, Bank bank, String Tin_number) {
            super(name, email, phoneNumber, bank);
            this.Tin_number = Tin_number;
        }

        public String getTin_number(){
            return this.Tin_number;
        }
    }

    public static class Organization extends Customer{

        String Bin_number;
        public Organization(String name, String email, String phoneNumber, Bank bank, String Bin_number) {
            super(name, email, phoneNumber, bank);
            this.Bin_number = Bin_number;
        }

        public String getBin_number(){
            return this.Bin_number;
        }
    }


}

