package com.asif.client;
import com.asif.bank.Bank;

import java.util.ArrayList;
import java.util.List;

public class Client {

    protected String name;
    protected String email;
    protected String phoneNumber;
    protected Bank bank;

    public Client(String name, String email, String phoneNumber, Bank bank) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bank = bank;
    }

    public String bank_name() {
        return this.bank.getBankName();
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }




}

