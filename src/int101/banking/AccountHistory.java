package int101.banking;

public class AccountHistory {
    private final AccountTransaction history[];
    private int count;

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

    @Override
    public String toString() { return this.toString("\n"); }
    
    public String toString(String separator) {
        StringBuilder sb = new StringBuilder();
        if (separator==null) separator = "";
        for (int i = 0; i < count; i++) {
            sb.append(history[i].toString());
            if (i<count-1) sb.append(separator);
        }
        return sb.toString();
    }
}
