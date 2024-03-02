package com.asif;

import com.asif.bank.*;
import com.asif.client.*;
import com.asif.account.*;

import java.util.Scanner;

public class SystemMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bankAsia = new Bank("Bank Asia", "123 Bank Street");
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Bank Asia Menu ===");
            System.out.println("1. Display Bank Info");
            System.out.println("2. Add Single Person Customer");
            System.out.println("3. Transfer Balance");
            System.out.println("4. Display Customer Info");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.println("Bank Name: " + bankAsia.getBankName());
                    System.out.println("Bank Address: " + bankAsia.getBankAddress());
                    break;

                case 2:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Phone Number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter Account Number: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Enter TIN Number: ");
                    String tinNumber = scanner.nextLine();

                    Customer.SinglePerson customer = new Customer.SinglePerson(name, email, phone, bankAsia, accountNumber, tinNumber);
                    bankAsia.addCustomer(customer);
                    System.out.println("Customer added successfully.");

                    break;

                case 3:
                    System.out.print("Enter Sender Account Number: ");
                    String senderAccountNumber = scanner.nextLine();
                    System.out.print("Enter Recipient Account Number: ");
                    String recipientAccountNumber = scanner.nextLine();
                    System.out.print("Enter Amount to Transfer: ");
                    double amount = scanner.nextDouble();

                    bankAsia.transferBalance(senderAccountNumber, recipientAccountNumber, amount);
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    String searchAccountNumber = scanner.nextLine();
                    System.out.println(bankAsia.getCustomerInfoByAccountNumber(searchAccountNumber));
                    break;

                case 5:
                    exit = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        scanner.close();
    }
}
