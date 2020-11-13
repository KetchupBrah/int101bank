package int101.banking;

import int101.base.Person;
import java.math.BigDecimal;

public class BankAccount {
    private static int nextAccountNo;
    private final int accountNo;
    private final String accountName;
    private final Person accountOwner;
    private final AccountHistory history;
    private BigDecimal balance;

    public BankAccount(Person accountOwner, String accountName) {
        this.accountNo = nextAccountNo++;
        this.accountName = accountName != null ? accountName : 
                accountOwner.getFirstname() + " " + accountOwner.getLastname();
        this.accountOwner = accountOwner;
        this.history = new AccountHistory(10);
        this.balance = new BigDecimal(0);
        this.history.append(new AccountTransaction(TransactionType.OPEN, this.balance));
    }
    
    public BankAccount(Person accountOwner) { this(accountOwner, null); }
    public int getAccountNo() { return accountNo; }
    public double getBalance() { return balance.doubleValue(); }
    public Person getAccountOwner() { return accountOwner; }
    public BankAccount deposit(double amount) { return deposit(amount, true); }    
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
    
    /* ToDo:
       - check if to Account is not null first.
       - try withdraw from this account first (call withdraw()); if fails, return null.
       - deposit to the other account (call deposit()); if fails, return null.
       - if everything is ok, return this (for method chaining).
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
        return "BankAccount[" + accountNo + ":" + accountName + "=" + balance + ']';
    }
    
    public String historyToString(String separator) {
        return history.toString(separator);
    }
}
