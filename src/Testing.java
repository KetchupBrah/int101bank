
import int101.banking.AccountHistory;
import int101.banking.AccountTransaction;
import int101.banking.BankAccount;
import int101.banking.TransactionType;
import int101.bank.Bank;
import int101.base.Person;
import java.math.BigDecimal;

public class Testing {

    public static void main(String[] args) {
        //testPerson();
        //testBankAccount();
        //testAccountHistory();
        testBanking();
    }

    private static void testPerson() {
        Person p0 = new Person("Alan","Turing");
        Person p1 = new Person("Alonzo","Chruch");
        Person p2 = new Person("Haskell","Curry");
        System.out.println(p0 + "\n" + p1 + "\n" + p2);
        p0.setLastname("Kay");
        System.out.println("Turing change to Kay");        
        p1.setFirstname("John").setLastname("McCarthy");
        System.out.println("Alonzo Church change to John McCarthy");
        p2.setFirstname("James").setLastname("Gosling");
        System.out.println("Haskell Curry change to James Gosling");
        System.out.println(p0);
        System.out.println(p1);
        System.out.println(p2);
    }

    private static void testBankAccount() {
        Person p0 = new Person("Alan","Turing");
        Person p1 = new Person("Alonzo","Chruch");
        Person p2 = new Person("Alan","Kay");
        BankAccount ac0 = new BankAccount(p0, "Turing Account");
        BankAccount ac1 = new BankAccount(p1, "Functional Account");
        BankAccount ac2 = new BankAccount(p2, "Object Account");
        System.out.println(ac0 + "\n" + ac1 + "\n" + ac2);
        System.out.println("new account -> deposit 200, deposit 3000, withdraw 380");
        System.out.println(ac0.deposit(200).deposit(3000).withdraw(380));
        System.out.println("new account -> withdraw 2000: " + ac1.withdraw(2000));
        System.out.println("new account -> deposit 500: " + ac2.deposit(500));
        System.out.println("transfer to new account 400 and 10000");
        ac0.transferTo(ac1, 400).transferTo(ac2, 10000);
        System.out.println(ac0 + "\n" + ac1 + "\n" + ac2);        
    }

    private static void testAccountHistory() {
        AccountHistory ah = new AccountHistory(10);
        ah.append(new AccountTransaction(TransactionType.OPEN,new BigDecimal(0)));
        ah.append(new AccountTransaction(TransactionType.DEPOSIT,new BigDecimal(1000)));
        ah.append(new AccountTransaction(TransactionType.WITHDRAW,new BigDecimal(200)));
        ah.append(new AccountTransaction(TransactionType.TRANSFER_OUT,new BigDecimal(500)));
        ah.append(new AccountTransaction(TransactionType.TRANSFER_IN,new BigDecimal(300)));
        System.out.println(ah);
    }
    
    private static void testBanking() {
        // just create some dummy person/account to advance the ids to some values
        var p = new Person("Dummy","Foobar"); new Person("Dummy","Foobar");
        new Person("Dummy","Foobar"); new Person("Dummy","Foobar");
        new Person("Dummy","Foobar"); new Person("Dummy","Foobar");
        new BankAccount(p); new BankAccount(p); new BankAccount(p);
        new BankAccount(p); new BankAccount(p); new BankAccount(p);
        new BankAccount(p); new BankAccount(p); new BankAccount(p);
        
        Bank b = new Bank();
        int c0 = b.newCustomer("Alan", "Turing");
        System.out.println("Add a new customer: Alan Turing : " + 
                (c0 < 0 ? "Fail." : b.customerToString(c0)));
        int c1 = b.newCustomer("Jim", "Carrey");
        System.out.println("Add a new customer: Jim Carrey : " +
                (c1 < 0 ? "Fail." : b.customerToString(c1)));
        int cid = b.getCustomerId(c1);
        int idx = b.findCustomerById(cid);
        System.out.println("Find Customer id = " + cid + " : " +
                (idx < 0 ? "Fail." : b.customerToString(idx)));
        idx = b.findCustomerByName("Alan", "Turing");
        System.out.println("Find Customer Name = Alan Turing : " +
                (idx < 0 ? "Fail." : b.customerToString(idx)));
        b.changeCustomerLastname(idx, "Kay");
        System.out.println("Change Customer Name from Alan Turing to: " +
                (idx < 0 ? "Fail." : b.customerToString(idx)));
        idx = b.findCustomerByName("Jim", "Carrey");
        if (idx<0) { 
            System.out.println("Fail");
        } else {
            b.changeCustomerFirstname(idx, "Mariah");
            b.changeCustomerLastname(idx, "Carey");
        System.out.println("Change Customer Name from Jim Carrey to: " +
                (idx < 0 ? "Fail." : b.customerToString(idx)));
        }
        
        int a0 = b.newAccount(c0, null);
        int a1 = b.newAccount(c1, null);
        int a2 = b.newAccount(c1, null);
        int a3 = b.newAccount(c0, null);
        System.out.println("Get Account No: " + 
                (a0<0 ? "Fail" : b.getAccountNo(a0)));
        b.deposit(a0, 4000).deposit(a0, 1500).withdraw(a0, 3050);
        System.out.println("+4000+1500-3050 = " + b.getBalance(a0));
        System.out.println("Account :: " + b.accountToString(a0));
        System.out.println("Account History :: \n   " + b.accountHistoryToString(a0,"\n   "));
        System.out.println("Find Account: " + b.accountToString(b.findAccount(b.getAccountNo(a3))));
        System.out.println("Transfer Money 1275 :: " + 
                (b.transferTo(a0, a2, 1115)==null ? "fail" : "success"));
        System.out.println("Accounts owned by " + b.customerToString(c0));
        for (idx = b.getAccountOwnedBy(c0); idx > -1; idx = b.getNextAccountOwnedBy(c0, idx)) {
            System.out.println("   account: " + b.accountToString(idx));
        }
        System.out.println("Accounts owned by " + b.customerToString(c1));
        for (idx = b.getAccountOwnedBy(c1); idx > -1; idx = b.getNextAccountOwnedBy(c1, idx)) {
            System.out.println("   account: " + b.accountToString(idx));
        }
        System.out.println("Number of Customers: " + b.getCustomerCount());
        System.out.println("Number of Accounts: " + b.getAccountCount());
    }
}
