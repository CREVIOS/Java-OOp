package com.asif.bank;


import com.asif.client.Customer;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    String bank_name;
    String bank_address;

    ArrayList<Customer> clients  = new ArrayList<>();


    public Bank(String bank_name, String bank_address){
        this.bank_address = bank_address;
        this.bank_name = bank_name;
    }

    public String getBankName(){
        return this.bank_name;
    }

    public String getBankAddress() {
        return this.bank_address;
    }

    public void addCustomer(Customer customer){
        clients.add(customer);
    }

    public String getCustomerInfoByAccountNumber(String accountNumber) {
        for (Customer customer : clients) {
            if (customer.getAccountNumber().equals(accountNumber)) {

               String info =  "Name: " + customer.getName() + ", Email: " + customer.getEmail() +
                        ", Phone: " + customer.getPhoneNumber() + ", Account Number: " + customer.getAccountNumber();

                if (customer instanceof Customer.SinglePerson) {
                    info += ", TIN: " + ((Customer.SinglePerson) customer).getTin_number();
                } else if (customer instanceof Customer.Organization) {
                    info += ", BIN: " + ((Customer.Organization) customer).getBin_number();
                }
                return info;
            }
        }
        return "No customer found with account number: " + accountNumber;
    }



    public String transferBalance(String senderAccountNumber, String recipientAccountNumber, double amount) {
        Customer sender = null;
        Customer recipient = null;

        // Find sender and recipient by their account numbers
        for (Customer customer : clients) {
            if (customer.getAccountNumber().equals(senderAccountNumber)) {
                sender = customer;
            } else if (customer.getAccountNumber().equals(recipientAccountNumber)) {
                recipient = customer;
            }

            // Break loop if both sender and recipient are found
            if (sender != null && recipient != null) {
                break;
            }
        }

        // Check if sender and recipient are valid
        if (sender == null) {
            return "Sender with account number " + senderAccountNumber + " not found.";
        }
        if (recipient == null) {
            return "Recipient with account number " + recipientAccountNumber + " not found.";
        }

        // Check if sender has enough balance
        if (sender.getAccount().getBalance() < amount) {
            return "Sender does not have enough balance to complete the transfer.";
        }

        // Proceed with the transfer
        sender.getAccount().withdraw(amount); // Subtract the amount from the sender's account
        recipient.getAccount().deposit(amount); // Add the amount to the recipient's account

        return "Transfer of " + amount + " from account " + senderAccountNumber +
                " to account " + recipientAccountNumber + " completed successfully.";
    }


}
