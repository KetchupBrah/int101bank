package int101.banking;

public class AccountHistory {
    private final AccountTransaction history[];
    private int count;
    
    public AccountHistory() { this(0); }

    public AccountHistory(int size) {
        history = new AccountTransaction[size>0 ? size :100];
    }
    
    public AccountHistory append(AccountTransaction trx) {
        if (count < history.length) {
            history[count++] = trx;
            return this;
        }
        return null;
    }

    public AccountTransaction getTransactionAt(int i) { return history[i]; }
    public int getCount() { return count; }
    public boolean isFull() { return count==history.length; }

    String toString(String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(history[i]);
            if (i+1<count) sb.append(separator);
        }
        return sb.toString();
    }
}
