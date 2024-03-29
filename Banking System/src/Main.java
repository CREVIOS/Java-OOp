import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static class Bank {
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
                    // Basic customer information
                    String info = "Name: " + customer.getName() + ", Email: " + customer.getEmail() +
                            ", Phone: " + customer.getPhoneNumber() + ", Account Number: " + customer.getAccountNumber();

                    // Extend info based on customer type
                    if (customer instanceof Customer.SinglePerson) {
                        info += ", TIN: " + ((Customer.SinglePerson) customer).getTin_number();
                    } else if (customer instanceof Customer.Organization) {
                        info += ", BIN: " + ((Customer.Organization) customer).getBin_number();
                    }
                    // No else needed as generic Customer doesn't have additional info to append
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


    public static class Client {

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

    public  static class Customer extends Client {

        protected String account_number;
        protected Account account; // Link to Account

        public Customer(String name, String email, String phoneNumber, Bank bank) {
            super(name, email, phoneNumber, bank);
            this.account_number = null;     }

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

        public static class SinglePerson extends Customer {

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



    public static abstract class Account {
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


        public void withdrawUsingCheque(double amount) {
            // Implement cheque withdrawal logic
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew " + amount + " using cheque. New balance: " + balance);
            } else {
                System.out.println("Insufficient balance for cheque withdrawal.");
            }
        }


        public void withdrawToBkash(double amount, String bkashNumber) {
            // Implement Bkash withdrawal logic
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Transferred " + amount + " to Bkash number " + bkashNumber + ". New balance: " + balance);
            } else {
                System.out.println("Insufficient balance for Bkash transfer.");
            }
        }


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

    public static class SalaryAccount extends Account {
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


    public static class SavingsAccount extends Account {
        private final static double INTEREST_RATE = 0.025; // 2.5%

        public SavingsAccount(double balance) {
            super(balance);
        }

        @Override
        public double calculateInterest(int years) {
            return balance * Math.pow(1 + INTEREST_RATE, years);
        }
    }


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
                            SalaryAccount salaryAccount = new SalaryAccount(0);
                            salaryAccount.deposit(amount);
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