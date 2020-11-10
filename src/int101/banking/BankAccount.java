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

    public BankAccount(String accountName, Person accountOwner) {
        this.accountNo = nextAccountNo++;
        this.accountName = accountName;
        this.accountOwner = accountOwner;
        this.history = new AccountHistory(10);
        this.balance = new BigDecimal(0);
        this.history.append(new AccountTransaction(TransactionType.OPEN, this.balance));
    }
    
    /* ToDo: 
       - call the above constructor to the the job.
       - use "firstname lastname" of accountOwner as the accountName;
    */
    public BankAccount(Person accountOwner) {
        this(accountOwner.getFirstname() + 
                " " + accountOwner.getLastname(), accountOwner);
    }

    public int getAccountNo() { return accountNo; }
    
    public BankAccount deposit(double amount) {
        return deposit(amount, true);
    }
    
    private BankAccount deposit(double amount, boolean log) {
        if (amount<=0) return null;
        BigDecimal d = new BigDecimal(amount);
        balance = balance.add(d);
        if (log) this.history.append(new AccountTransaction(TransactionType.DEPOSIT, d));
        return this;
    }
    
    public BankAccount withdraw(double amount) {
        return withdraw(amount, true);
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

    public Person getAccountOwner() { return accountOwner; }

    @Override
    public String toString() {
        return "BankAccount[" + accountNo + ":" + accountName + "=" + balance + ']';
    }
    
}
