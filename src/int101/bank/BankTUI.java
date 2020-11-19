package int101.bank;

import java.util.Scanner;

public class BankTUI {
    
    public static final String END = "\u001B[0m";
    public static final String INFO = "\u001B[46m";
    public static final String WARN = "\u001B[31m\u001B[46m";

    private final Bank bank;
    private final Scanner scan;

    public static void main(String[] args) {
        BankTUI ui = new BankTUI();
        ui.repl();
    }

    public BankTUI() {
        bank = new Bank();
        scan = new Scanner(System.in);
    }

    public void repl() { // read-evaluate-print loop
        int chosen;
        do {
            System.out.println("\nBanking Services");
            System.out.println("  0: Exit");
            System.out.println("  1: Customer Management");
            System.out.println("  2: Account Management");
            System.out.print(" Your Selection : ");
            chosen = scan.nextInt();
            scan.nextLine();
            switch (chosen) {
                case 1 -> customerManagement();
                case 2 -> accountManagement();             }
        } while (chosen != 0);
    }

    public void customerManagement() {
        int chosen;
        do {
            System.out.println("\nCustomer Management");
            System.out.println("  0: Exit");
            System.out.println("  1: List All Customers");
            System.out.println("  2: Add a New Customer");
            System.out.print("Your Selection : ");
            chosen = scan.nextInt();
            scan.nextLine();
            switch (chosen) {
                case 1 -> listAllCustomers();
                case 2 -> addNewCustomer();
            }
        } while (chosen != 0);
    }
    
    public void listAllCustomers() {
        int size = bank.getCustomerCount();
        if (size==0) {
            System.out.println(INFO+"There is no customer."+END);
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(INFO+bank.customerToString(i)+END);
        }
    }
    
    public void addNewCustomer() {
        if (bank.areCustomersFull()) {
            System.out.println(INFO+"No more space for a new customer."+END);
            return;
        }
        System.out.print("Firstname : ");
        String fn = scan.nextLine();
        System.out.print("Lastname : ");
        String ln = scan.nextLine();
        int custIdx = bank.newCustomer(fn, ln);
        if (custIdx<0) {
            System.out.println(WARN+"Fail: duplicate customer name is not allowed."+END);
            return;
        }
        System.out.println(INFO+"New Customer: " + bank.customerToString(custIdx) + END);
    }

    public void accountManagement() {
        int chosen;
        do {
            System.out.println("\nAccount Management");
            System.out.println("  0: Exit");
            System.out.println("  1: List All Accounts");
            System.out.println("  2: List All Account Transactions");
            System.out.println("  3: Add a new Account");
            System.out.print("Your Selection : ");
            chosen = scan.nextInt();
            scan.nextLine();
            switch (chosen) {
                case 1 -> listAllAccounts();
                case 2 -> listAllAccountTransactions();
                case 3 -> addNewAccount();
            }
        } while (chosen != 0);
    }
    
    public void listAllAccounts() {
        int size = bank.getAccountCount();
        if (size==0) {
            System.out.println(INFO+"There is no account."+END);
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(INFO+bank.accountToString(i)+END);
        }
    }
    
    public void listAllAccountTransactions() {
        int size = bank.getAccountCount();
        if (size==0) {
            System.out.println(WARN+"There is no account."+END);
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(i + ". " + bank.accountToString(i));
        }
        System.out.print("Choose the account to view all of its transaction details: ");
        int accIdx = scan.nextInt();
        scan.nextLine();
        if (accIdx<0 || accIdx>=size) {
            System.out.println(WARN+"There is no such an account."+END);
            return;
        }
        System.out.println(bank.accountToString(accIdx));
        int trxIdx = bank.getAccountTransactionCount(accIdx);
        for (int i = 0; i < trxIdx; i++) {
            System.out.println(INFO+bank.getAccounTransactionAt(accIdx, i).toString()+END);
        }
    }
    
    public void addNewAccount() {
        if (bank.areAccountsFull()) {
            System.out.println(WARN+"No more space for a new account."+END);
            return;
        }
        int size = bank.getCustomerCount();
        if (size==0) {
            System.out.println(WARN+"There is no customer to be the owner."+END);
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(i + ". " + bank.customerToString(i));
        }
        System.out.print("Choose the customer to be the owner of the new account: ");
        int custIdx = scan.nextInt();
        scan.nextLine();
        if (custIdx<0 || custIdx>=size) {
            System.out.println(WARN+"There is no such a customer."+END);
            return;
        }
        System.out.print("Account Name : ");
        String accName = scan.nextLine();
        int newAccIdx = bank.newAccount(custIdx, accName);
        if (newAccIdx<0) {
            System.out.println(WARN+"Fail: Cannot create a new account."+END);
            return;
        }
        System.out.println(INFO+"New Account: " + bank.accountToString(newAccIdx)+END);
    }
}
