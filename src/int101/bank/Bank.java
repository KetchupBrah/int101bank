package int101.bank;

import int101.banking.BankAccount;
import int101.base.Person;

public class Bank {
    
    private Person customers[]; 
    private BankAccount accounts[];
    private int accountOwners[];
    private int customerCount;
    private int accountCount;

    public Bank(int size) {
        size = size>0 ? size : 10;
        customers = new Person[size];
        accounts = new BankAccount[size];
        accountOwners = new int[size];
    }
    
    public Bank() { this(10); }
    
    public int getCustomerId(int idx) { 
        return idx<customerCount ? customers[idx].getId() : -1;
    }
    public String getCustomerFirstname(int idx) { 
        return idx<customerCount ? customers[idx].getFirstname() : null;
    }
    public String getCustomerLastname(int idx) { 
        return idx<customerCount ? customers[idx].getLastname() : null;
    }
    
    /* return the index position to the customer */
    public int findCustomer(String firstname, String lastname) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].matchName(firstname, lastname)) return i;
        }
        return -1;
    }
    
    /* return the index position to the customer */
    public int findCustomer(int id) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getId()==id) return i;
        }
        return -1;
    }

    /* 
       not allow to add a new customer having 
       the same firstname and lastname as an existing customer 
    */
    public int newCustomer(String firstname, String lastname) {
        if (customerCount==customers.length) return -1;
        if (findCustomer(firstname, lastname)>-1) return -1;
        customers[customerCount] = new Person(firstname, lastname);
        return customerCount++;
    }

    public Bank changeCustomerFirstname(int index, String firstname) {
        customers[index].setFirstname(firstname);
        return this;
    }

    public Bank changeCustomerLastname(int index, String lastname) {
        customers[index].setLastname(lastname);
        return this;
    }

    public int getAccountNo(int idx) { 
        return idx<accountCount ? accounts[idx].getAccountNo() : -1;
    }
    public double getBalance(int idx) { 
        return idx<accountCount ? accounts[idx].getBalance() : -1;
    }

    public int findAccount(int accountNo) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNo()==accountNo) return i;
        }
        return -1;
    }

    public int newAccount(int ownerIndex) { return newAccount(ownerIndex, null); }

    public int newAccount(int ownerIndex, String accountName) {
        if (accountCount==accounts.length) return -1;
        accounts[accountCount] = new BankAccount(customers[ownerIndex], accountName);
        accountOwners[accountCount] = ownerIndex;
        return accountCount++;
    }

    public int getAccountOwnedBy(int ownerIndex) {
        return getNextAccountOwnedBy(ownerIndex, -1);
    }
    
    public int getNextAccountOwnedBy(int ownerIndex, int afterAccountIndex) {
        for (int i = afterAccountIndex+1; i < accountCount; i++) {
            if (accountOwners[i] == ownerIndex) return i;
        }
        return -1;
    }
    
    public Bank deposit(int accountIndex, double amount) {
        return accounts[accountIndex].deposit(amount) != null ? this : null;
    }

    public Bank withdraw(int accountIndex, double amount) {
        return accounts[accountIndex].withdraw(amount) != null ? this : null;
    }

    public Bank transferTo(int accountFrom, int accountTo, double amount) {
        return accounts[accountFrom].transferTo(accounts[accountTo], amount) != null ? this : null;
    }
    
    public int getCustomerCount() { return customerCount; }
    public int getAccountCount() { return accountCount; }
    
    public String customerToString(int index) { return customers[index].toString(); }
    public String accountToString(int index) { return accounts[index].toString(); }
    public String accountHistoryToString(int index) { 
        return accounts[index].historyToString(null); 
    }
    public String accountHistoryToString(int index, String separator) { 
        return accounts[index].historyToString(separator); 
    }

}
