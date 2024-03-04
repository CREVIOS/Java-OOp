package com.asif.bank;


import com.asif.account.Account;
import com.asif.client.Customer;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    String bank_name;
    String bank_address;

    ArrayList<Customer> clients  = new ArrayList<>();


    public Bank(String bank_name, String bank_address){
        this.bank_address = bank_address;
        this.bank_name = bank_name;
    }



    public String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;
        boolean unique;

        do {
            unique = true;
            // Generate a random 8-digit number
            accountNumber = String.format("%08d", random.nextInt(100000000));

            // Check if it's unique
            for (Customer customer : clients) {
                if (customer.getAccountNumber().equals(accountNumber)) {
                    unique = false;
                    break;
                }
            }
        } while (!unique);

        return accountNumber;
    }



    public String getBankName(){
        return this.bank_name;
    }

    public String getBankAddress() {
        return this.bank_address;
    }


        public String addCustomer(Customer customer) {
        String uniqueAccountNumber = generateUniqueAccountNumber();
        customer.setAccountNumber(uniqueAccountNumber);
        clients.add(customer);
        return uniqueAccountNumber;
    }


    //public void addCustomer(Customer customer){
    //    clients.add(customer);
  //  }

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



    public void transferBalance(String senderAccountNumber, String recipientAccountNumber, double amount) {
        Customer sender = null;
        Customer recipient = null;

        for (Customer customer : clients) {
            if (customer.getAccountNumber().equals(senderAccountNumber)) {
                sender = customer;
            } else if (customer.getAccountNumber().equals(recipientAccountNumber)) {
                recipient = customer;
            }

            if (sender != null && recipient != null) {
                break;
            }
        }

        if (sender == null) {
            System.out.println("Sender with account number " + senderAccountNumber + " not found.");
            return;
        }
        if (recipient == null) {
            System.out.println("Recipient with account number " + recipientAccountNumber + " not found.");
            return;
        }

        if (sender.getAccount().getBalance() < amount) {
            System.out.println("Sender does not have enough balance to complete the transfer.");
            return;
        }


        sender.getAccount().withdraw(amount);
        recipient.getAccount().deposit(amount);

        System.out.println("Transfer of " + amount + " from account " + senderAccountNumber +
                " to account " + recipientAccountNumber + " completed successfully.");
        return;
    }

    public String getCustomerBalanceByAccountNumber(String accountNumber) {
        for (Customer customer : clients) {
            if (customer.getAccountNumber().equals(accountNumber)) {
                return "The balance for account number " + accountNumber + " is: " + customer.getAccount().getBalance();
            }
        }
        return "No customer found with account number: " + accountNumber;
    }

    public Customer getCustomerByAccountNumber(String accountNumber) {
        for (Customer customer : clients) {
            if (customer.getAccountNumber().equals(accountNumber)) {
                return customer;
            }
        }
        return null; // Or any other indication that the customer was not found.
    }


    public void withdrawUsingCheque(String accountNumber, double amount) {
        Customer customer = findCustomerByAccountNumber(accountNumber);
        if (customer != null) {
            Account account = customer.getAccount();
            account.withdrawUsingCheque(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdrawToBkash(String accountNumber, double amount, String bkashNumber) {
        Customer customer = findCustomerByAccountNumber(accountNumber);
        if (customer != null) {
            Account account = customer.getAccount();
            account.withdrawToBkash(amount, bkashNumber);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdrawUsingCreditCard(String accountNumber, double amount) {
        Customer customer = findCustomerByAccountNumber(accountNumber);
        if (customer != null) {
            Account account = customer.getAccount();
            account.withdrawUsingCreditCard(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private Customer findCustomerByAccountNumber(String accountNumber) {
        for (Customer customer : clients) {
            if (customer.getAccountNumber().equals(accountNumber)) {
                return customer;
            }
        }
        return null;
    }


}
