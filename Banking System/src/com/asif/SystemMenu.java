package com.asif;

import com.asif.bank.*;
import com.asif.client.*;
import com.asif.account.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemMenu {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Bank> banks = new ArrayList<>();
        banks.add(new Bank("Bank Asia", "123 Bank Street"));
        banks.add(new Bank("Bank Pacific", "456 Ocean Avenue"));
        banks.add(new Bank("Bank Global", "789 Global Lane"));
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("0. Exit");
            for (int i = 0; i < banks.size(); i++) {
                System.out.println((i + 1) + ". " + banks.get(i).getBankName() + " Operations");
            }
            System.out.print("Enter your choice: ");
            int bankChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            if (bankChoice == 0) {
                System.out.println("Exiting...");
                exit = true;
                continue;
            }

            if (bankChoice < 1 || bankChoice > banks.size()) {
                System.out.println("Invalid choice. Please select a valid bank.");
                continue;
            }

            Bank selectedBank = banks.get(bankChoice - 1);
            boolean bankExit = false;

            while (!bankExit) {
                System.out.println("\n=== " + selectedBank.getBankName() + " Menu ===");
                System.out.println("1. Display Bank Info");
                System.out.println("2. Add Single Person Customer");
                System.out.println("3. Transfer Balance");
                System.out.println("4. Display Customer Info");
                System.out.println("5. Balance Inquiry");
                System.out.println("6. Withdraw amount");
                System.out.println("7. Return to Main Menu");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                switch (choice) {
                    case 1:
                        System.out.println("Bank Name: " + selectedBank.getBankName());
                        System.out.println("Bank Address: " + selectedBank.getBankAddress());
                        break;

                    case 2:
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter Phone Number: ");
                        String phone = scanner.nextLine();
                        System.out.print("Enter TIN Number: ");
                        String tinNumber = scanner.nextLine();

                        Customer.SinglePerson customer = new Customer.SinglePerson(name, email, phone, selectedBank, tinNumber);
                        String accountNumber = selectedBank.addCustomer(customer);
                        System.out.println("Your Account Number: " + accountNumber);
                        System.out.println("Customer added successfully.");

                        System.out.println("\n=== Account Type ===");
                        System.out.println("1. Saving Account");
                        System.out.println("2. Salary Account");
                        System.out.print("Enter your choice: ");
                        int ac_choice = scanner.nextInt();

                        // Correcting the loop condition
                        while (ac_choice != 1 && ac_choice != 2) {
                            System.out.println("Invalid choice. Please enter 1 for Saving Account or 2 for Salary Account.");
                            ac_choice = scanner.nextInt();
                        }

                        System.out.print("Enter The Amount to be added to the account: ");
                        double amount = scanner.nextDouble();

                        if (ac_choice == 1) {
                            SavingsAccount savingsAccount = new SavingsAccount(amount);
                            customer.setAccount(savingsAccount);
                        } else {
                            SalaryAccount salaryAccount = new SalaryAccount(amount);
                            customer.setAccount(salaryAccount);
                        }
                        System.out.println("Account created and amount added successfully!");
                        break;

                    case 3:
                        System.out.print("Enter Sender Account Number: ");
                        String senderAccountNumber = scanner.nextLine();
                        System.out.print("Enter Recipient Account Number: ");
                        String recipientAccountNumber = scanner.nextLine();
                        System.out.print("Enter Amount to Transfer: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline left-over
                        selectedBank.transferBalance(senderAccountNumber, recipientAccountNumber, amount);
                        break;

                    case 4:
                        System.out.print("Enter Account Number: ");
                        String searchAccountNumber = scanner.nextLine();
                        System.out.println(selectedBank.getCustomerInfoByAccountNumber(searchAccountNumber));
                        break;

                    case 5:
                        System.out.print("Enter Account Number for Balance Inquiry: ");
                        String inquiryAccountNumber = scanner.nextLine();
                        System.out.println(selectedBank.getCustomerBalanceByAccountNumber(inquiryAccountNumber));
                        break;

                    case 6:
                        boolean backToMainMenu = false; // Flag to control the loop
                        while (!backToMainMenu) {
                            System.out.println("\n=== Withdraw Menu ===");
                            System.out.println("1. Withdraw Using Cheque");
                            System.out.println("2. Withdraw to Bkash");
                            System.out.println("3. Withdraw Using Credit Card");
                            System.out.println("4. Return to Previous Menu");
                            System.out.print("Enter your choice: ");
                            int withdrawChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline left-over

                            switch (withdrawChoice) {
                                case 1:
                                    System.out.print("Enter Account Number: ");
                                    String accountForCheque = scanner.nextLine();
                                    System.out.print("Enter Amount to Withdraw: ");
                                    double chequeAmount = scanner.nextDouble();
                                    scanner.nextLine(); // Consume newline left-over
                                    selectedBank.withdrawUsingCheque(accountForCheque, chequeAmount);
                                    //System.out.println("Withdrawal of " + chequeAmount + " using cheque from account " + accountForCheque + " processed.");
                                    break;

                                case 2 :
                                    System.out.print("Enter Account Number: ");
                                    String accountForBkash = scanner.nextLine();
                                    System.out.print("Enter Bkash Number: ");
                                    String bkashNumber = scanner.nextLine();
                                    System.out.print("Enter Amount to Withdraw: ");
                                    double bkashAmount = scanner.nextDouble();
                                    scanner.nextLine(); // Consume newline left-over
                                    selectedBank.withdrawToBkash(accountForBkash, bkashAmount, bkashNumber);
                                    //System.out.println("Withdrawal of " + bkashAmount + " to Bkash number " + bkashNumber + " from account " + accountForBkash + " processed.");
                                    break;

                                case 3:
                                    System.out.print("Enter Account Number: ");
                                    String accountForCreditCard = scanner.nextLine();
                                    System.out.print("Enter Amount to Withdraw: ");
                                    double creditCardAmount = scanner.nextDouble();
                                    scanner.nextLine(); // Consume newline left-over
                                    selectedBank.withdrawUsingCreditCard(accountForCreditCard,creditCardAmount);
                                    //System.out.println("Withdrawal of " + creditCardAmount + " using credit card from account " + accountForCreditCard + " processed.");
                                    break;

                                case 4:
                                    backToMainMenu = true;
                                    break;

                                default:
                                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                            }
                        }

                    case 7: // Exit
                        exit = true;
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            }

            scanner.close();
        }
    }


}