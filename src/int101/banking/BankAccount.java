package int101.banking;

import int101.base.Person;
import java.math.BigDecimal;

public class BankAccount {
    
    /**
     * a running number for "Account No" 
     */
    private static int nextAccountNo = 1001;
    
    /**
     * the unique Account No of this account
     */
    private final int accountNo;
    
    /**
     * the name of this account
     */
    private final String accountName;
    
    /**
     * the owner of this account
     */
    private final Person accountOwner;
    
    /**
     * transaction history for this account
     */
    private final AccountHistory history;
    
    /**
     * the current balance of this account
     */
    private BigDecimal balance;

    /**
     * Constructor
     * if accountName is null, use Firstname and Lastname of owner instead.
     * 
     * @param accountOwner
     * @param accountName 
     */
    public BankAccount(Person accountOwner, String accountName, int maxTransactions) {
        this.accountNo = nextAccountNo++;
        this.accountName = accountName != null ? accountName : 
                accountOwner.getFirstname() + " " + accountOwner.getLastname();
        this.accountOwner = accountOwner;
        this.history = new AccountHistory(maxTransactions>0 ? maxTransactions : 100);
        this.balance = new BigDecimal(0);
        this.history.append(new AccountTransaction(TransactionType.OPEN, this.balance));
    }
    
    public BankAccount(Person accountOwner, String accountName) {
        this(accountOwner, accountName, 0);
    }
    
    public BankAccount(Person accountOwner) {
        this(accountOwner, null, 0);
    }
    
    public int getAccountNo() { return accountNo; }
    public double getBalance() { return balance.doubleValue(); }
    public Person getAccountOwner() { return accountOwner; }
    
    /**
     * @param amount must be positive
     * @return null if fail
     */
    public BankAccount deposit(double amount) { return deposit(amount, true); }
    
    /**
     * @param amount must be positive
     * @return null if fail
     */
    public BankAccount withdraw(double amount) { return withdraw(amount, true); }
    
    private BankAccount deposit(double amount, boolean log) {
        if (amount<=0) return null;
        BigDecimal d = new BigDecimal(amount);
        balance = balance.add(d);
        if (log) this.history.append(new AccountTransaction(TransactionType.DEPOSIT, d));
        return this;
    }
    
    private BankAccount withdraw(double amount, boolean log) {
        if (amount<=0) return null;
        if (balance.doubleValue()<amount) return null;
        BigDecimal d = new BigDecimal(amount);
        balance = balance.subtract(d);
        if (log) this.history.append(new AccountTransaction(TransactionType.WITHDRAW, d));
        return this;
    }

    /**
     * @param to the account to transfer to
     * @param amount must be positive
     * @return null if fail
     */
    public BankAccount transferTo(BankAccount to, double amount) {
        if (to==null) return null;
        if (withdraw(amount, false)==null) return null;
        to.deposit(amount, false);
        BigDecimal d = new BigDecimal(amount);
        this.history.append(new AccountTransaction(TransactionType.TRANSFER_OUT, d));
        to.history.append(new AccountTransaction(TransactionType.TRANSFER_IN, d));
        return this;
    }

    @Override
    public String toString() {
        return "BankAccount[ AccountNo : " + accountNo 
                + " : Account Name : " + accountName 
                + " : Balance : " + balance + " ]";
    }

    /**
     * @param i the position of the transaction of this account to read
     * @return transaction i of this account
     */
    public AccountTransaction getTransactionAt(int i) {
        return history.getTransactionAt(i);
    }

    /**
     * @return the number of transactions in this account
     */
    public int getHistoryCount() { return history.getCount(); }

    public boolean areTransactionsFull() { return history.isFull(); }

    public String historyToString(String separator) {
        return history.toString(separator);
    }

}
