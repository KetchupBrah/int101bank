package int101.banking;

import int101.base.Person;

public class Banking {
    
    private Person customers[]; 
    private BankAccount accounts[];
    private int customerCount;
    private int accountCount;

    public Banking(int size) {
        size = size>0 ? size : 10;
        customers = new Person[size];
        accounts = new BankAccount[size];
    }
    
    public Banking() { this(10); }
    
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
        if (findCustomer(firstname, lastname)==-1) return -1;
        customers[customerCount] = new Person(firstname, lastname);
        return customerCount++;
    }

    public Banking changeCustomerFirstname(int index, String firstname) {
        customers[index].setFirstname(firstname);
        return this;
    }

    public Banking changeCustomerLastname(int index, String lastname) {
        customers[index].setLastname(lastname);
        return this;
    }

    public int findAccount(int accountNo) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNo()==accountNo) return i;
        }
        return -1;
    }

    public int newAccount(int ownerIndex) {
        if (accountCount==accounts.length) return -1;
        accounts[accountCount] = new BankAccount(customers[ownerIndex]);
        return accountCount++;
    }

    public int newAccount(int ownerIndex, String accountName) {
        if (accountCount==accounts.length) return -1;
        accounts[accountCount] = new BankAccount(accountName, customers[ownerIndex]);
        return accountCount++;
    }

    public Banking deposit(int accountIndex, double amount) {
        return accounts[accountIndex].deposit(amount) != null ? this : null;
    }

    public Banking withdraw(int accountIndex, double amount) {
        return accounts[accountIndex].withdraw(amount) != null ? this : null;
    }

    public Banking transferTo(int accountFrom, int accountTo, double amount) {
        return accounts[accountFrom].transferTo(accounts[accountTo], amount) != null ? this : null;
    }
    
    public int getCustomerCount() { return customerCount; }
    public int getAccountCount() { return accountCount; }
    
    public String customerToString(int index) { return customers[index].toString(); }
    public String accountToString(int index) { return accounts[index].toString(); }

}
