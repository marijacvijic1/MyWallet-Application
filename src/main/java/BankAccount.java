import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BankAccount {

    private final User user;
    private final String accountNumber;
    private double balance;
    private final String accountAlias;
    private boolean bonusPaymentPayedOut = false;
    private CurrencyEnum currency;
    private final ArrayList<Transaction> transactionsList;

    public BankAccount(User user, String accountAlias) {
        this.user = user;
        this.accountAlias = accountAlias;
        this.balance = 0;
        this.accountNumber = createBankAccountNumber();
        this.currency = CurrencyEnum.RSD;
        this.transactionsList = new ArrayList<>();
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountAlias() {
        return accountAlias;
    }

    public User getUser() {
        return user;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public ArrayList<Transaction> getTransactionsList() {
        return transactionsList;
    }

    public boolean isBonusPaymentPayedOut() {
        return bonusPaymentPayedOut;
    }

    public void setBonusPaymentPayedOut(boolean bonusPaymentPayedOut) {
        this.bonusPaymentPayedOut = bonusPaymentPayedOut;
    }

    public void withdrawal(double withdrawalAmount) throws RuntimeException {
        if (balance < withdrawalAmount) {
            throw new RuntimeException("Not enough funds!");
        }
        setBalance(balance - withdrawalAmount);
        transactionsList.add(new Transaction(accountNumber, "", "withdrawal", withdrawalAmount));
    }

    public void makeExternalPayment(double withdrawalAmount, String bankAccount, String transactionCategory) throws RuntimeException {
        if (balance < withdrawalAmount) {
            throw new RuntimeException("Not enough funds!");
        }
        setBalance(balance - withdrawalAmount);
        transactionsList.add(new Transaction(accountNumber, bankAccount, transactionCategory, withdrawalAmount));
    }

    private String createBankAccountNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i=0; i<17; i++){
            if (i == 2) {
                stringBuilder.append("-");
            } else if (i == 15) {
                stringBuilder.append("-");
            }

            stringBuilder.append(random.nextInt(9));
        }

        return stringBuilder.toString();
    }

    private double calculateCurrencyBalance(double amountInDinars, CurrencyEnum currency) throws RuntimeException {
        switch (currency) {
            case USD:
                return amountInDinars / 99;
            case EURO:
                return amountInDinars / 118;
            default:
                throw new RuntimeException("Unsupported currency! Currencies supported are:" + Arrays.toString(CurrencyEnum.values()));
        }
    }

    public void setNewCurrencyBalance(double amountInDinars, CurrencyEnum newCurrency) throws RuntimeException {
        balance = calculateCurrencyBalance(amountInDinars, newCurrency);
        currency = newCurrency;
    }
}
