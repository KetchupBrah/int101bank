package int101.bank;

import int101.banking.AccountTransaction;
import int101.banking.BankAccount;
import int101.base.Person;

public class Bank {
    
    /**
     * an array of customers
     */
    private Person customers[];
    
    /**
     * an array of accounts
     */
    private BankAccount accounts[];
    
    /**
     * an array of account owners corresponding to accounts[].
     * E.g., accountOwners = {1,2,2,0} means
     * customers[0] owns accounts[3],
     * customers[1] owns accounts[0],
     * customers[2] owns accounts[1] and accounts[2]
     */
    private int accountOwners[];
    
    /**
     * the number of customers in customers[]
     */
    private int customerCount;
    
    /**
     * the number of accounts in accounts[]
     */
    private int accountCount;
    
    /**
     * the maximum number of transactions per accounts allowed
     */
    private int maxTransactions;

    /**
     * @param maxCustomers the maximum number of customers in this bank
     * @param maxAccounts the maximum number of accounts in this bank
     * @param maxTransactions the maximum number of transactions per account in this bank
     */
    public Bank(int maxCustomers, int maxAccounts, int maxTransactions) {
        maxAccounts = maxAccounts>0 ? maxAccounts : 10;
        this.maxTransactions = maxTransactions > 0 ? maxTransactions : 100;
        customers = new Person[maxCustomers > 0 ? maxCustomers : 10];
        accounts = new BankAccount[maxAccounts];
        accountOwners = new int[maxAccounts];
    }

    public Bank(int size) { this(size,size,0); }
    public Bank() { this(0,0,0); }
    
    /**
     * @param idx the index position 
     * @return -1 if no such a customer
     */
    public int getCustomerId(int idx) { 
        return idx<customerCount && idx>=0 ? customers[idx].getId() : -1;
    }
    
    /**
     * @param idx the index position
     * @return null if no such a customer
     */
    public String getCustomerFirstname(int idx) { 
        return idx<customerCount && idx>=0 ? customers[idx].getFirstname() : null;
    }
    
    /**
     * @param idx the index position
     * @return null if no such a customer
     */
    public String getCustomerLastname(int idx) { 
        return idx<customerCount && idx>=0 ? customers[idx].getLastname() : null;
    }
    
    /**
     * @param firstname
     * @param lastname
     * @return the index position of the customer that matches 
     * both firstname and lastname, otherwise return -1 if not found
     */
    public int findCustomerByName(String firstname, String lastname) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].matchName(firstname, lastname)) return i;
        }
        return -1;
    }
    
    /**
     * @param id
     * @return the index position of the customer that
     * matches the id, otherwise return -1 if not found
     */
    public int findCustomerById(int id) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getId()==id) return i;
        }
        return -1;
    }

    /**
     * create a new customer; not allow to add a new customer that 
     * has the same firstname and lastname as an existing customer 
     * @param firstname
     * @param lastname
     * @return -2 if no more space to add; -1 if duplicate name;
     * otherwise returns the index position of the newly added customer
     */
    public int newCustomer(String firstname, String lastname) {
        if (customerCount==customers.length) return -2;
        if (findCustomerByName(firstname, lastname)>-1) return -1;
        customers[customerCount] = new Person(firstname, lastname);
        return customerCount++;
    }

    /**
     * change firstname of the customer at the index position
     * @param index
     * @param firstname
     * @return true if successful; otherwise false
     */
    public boolean changeCustomerFirstname(int index, String firstname) {
        if (index < 0 || index >= customerCount) return false;
        return customers[index].setFirstname(firstname) != null;
    }

    /**
     * change lastname of the customer at the index position
     * @param index
     * @param lastname
     * @return true if successful; otherwise false
     */
    public Bank changeCustomerLastname(int index, String lastname) {
        customers[index].setLastname(lastname);
        return this;
    }

    /**
     * get the account no of the account at the idx position
     * @param idx
     * @return -1 if no account at the idx position
     */
    public int getAccountNo(int idx) { 
        return (idx>=0 && idx<accountCount) ? accounts[idx].getAccountNo() : -1;
    }
    
    /**
     * @param idx
     * @return -1 if no account at the idx position
     */
    public double getBalance(int idx) { 
        return (idx>=0 && idx<accountCount) ? accounts[idx].getBalance() : -1;
    }

    /**
     * @param accountNo
     * @return -1 if no such an accountNo; otherwise, return the index position
     */
    public int findAccount(int accountNo) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNo()==accountNo) return i;
        }
        return -1;
    }

    /**
     * @param ownerIndex
     * @param accountName
     * @return -2 if no more space for a new account; -1 if no such an account owner;
     * otherwise return the index position of the newly created account
     */
    public int newAccount(int ownerIndex, String accountName) {
        if (accountCount==accounts.length) return -2;
        if (ownerIndex < 0 || ownerIndex >= customerCount) return -1;
        accounts[accountCount] = new BankAccount(customers[ownerIndex], accountName, maxTransactions);
        accountOwners[accountCount] = ownerIndex;
        return accountCount++;
    }

    /**
     * get the first index position of the account owned by
     * the customer at the ownerIndex position (customers[ownerIndex])
     * @param ownerIndex
     * @return -1 if no more account owned by the customer.
     */
    public int getAccountOwnedBy(int ownerIndex) {
        return getNextAccountOwnedBy(ownerIndex, -1);
    }
    
    /**
     * get the index position of the next account after afterAccountIndex
     * owned by the customer at the ownerIndex position
     * @param ownerIndex
     * @param afterAccountIndex
     * @return -1 if no more account owned by the customer.
     */
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

    public Bank transferTo(int fromAccount, int toAccount, double amount) {
        return accounts[fromAccount].transferTo(accounts[toAccount], amount) != null ? this : null;
    }
    
    public int getCustomerCount() { return customerCount; }
    public int getAccountCount() { return accountCount; }
    public int getAccountTransactionCount(int accIdx) { return accounts[accIdx].getHistoryCount(); }
    public boolean areCustomersFull() { return customerCount==customers.length; }
    public boolean areAccountsFull() { return accountCount==accounts.length; }
    public boolean areTransactionsFull(int accIdx) {  return accounts[accIdx].areTransactionsFull(); }
    public String customerToString(int custIdx) { return customers[custIdx].toString(); }
    public String accountToString(int accIdx) { return accounts[accIdx].toString(); }
    public String accountHistoryToString(int accIdx, String separator) { 
        return accounts[accIdx].historyToString(separator);
    }
    public AccountTransaction getAccounTransactionAt(int accIdx, int trxIdx) { 
        return accounts[accIdx].getTransactionAt(trxIdx);
    }

}
