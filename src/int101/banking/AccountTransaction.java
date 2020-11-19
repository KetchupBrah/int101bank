package int101.banking;

import java.math.BigDecimal;

/**
 * AccountTransaction object is an immutable object 
 * consists of type of transactions (deposit,withdraw,...) 
 * and the amount involved in the transaction.
 */
public class AccountTransaction {
    private final TransactionType type;
    private final BigDecimal amount;

    public AccountTransaction(TransactionType type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

    public TransactionType getType() { return type; }
    public double getAmount() { return amount.doubleValue(); }

    @Override
    public String toString() {
        return "Transaction [ " + type + " : amount : " + amount + " ]";
    }
}
