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
                System.out.println("6. Withdraw Using Cheque");
                System.out.println("7. Withdraw to Bkash");
                System.out.println("8. Withdraw Using Credit Card");
                System.out.println("9. Return to Main Menu");
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

                    case 6: // Withdraw Using Cheque
                        System.out.print("Enter Account Number: ");
                        String accountForCheque = scanner.nextLine();
                        System.out.print("Enter Amount to Withdraw: ");
                        double chequeAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline left-over
                        selectedBank.withdrawUsingCheque(accountForCheque, chequeAmount);
                        break;

                    case 7: // Withdraw to Bkash
                        System.out.print("Enter Account Number: ");
                        String accountForBkash = scanner.nextLine();
                        System.out.print("Enter Bkash Number: ");
                        String bkashNumber = scanner.nextLine();
                        System.out.print("Enter Amount to Withdraw: ");
                        double bkashAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline left-over
                        selectedBank.withdrawToBkash(accountForBkash, bkashAmount, bkashNumber);
                        break;

                    case 8: // Withdraw Using Credit Card
                        System.out.print("Enter Account Number: ");
                        String accountForCreditCard = scanner.nextLine();
                        System.out.print("Enter Amount to Withdraw: ");
                        double creditCardAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline left-over
                        selectedBank.withdrawUsingCreditCard(accountForCreditCard,creditCardAmount);
                        break;

                    case 9: // Exit
                        exit = true;
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 9.");
                }
            }

            scanner.close();
        }
    }
}