import java.util.ArrayList;

public class Bank {
    private String bankName;
    private String address;
    private String ceo;
    private ArrayList<BankAccount> bankAccountsArray;

    public Bank(String bankName, String address, String ceo) {
        this.bankName = bankName;
        this.address = address;
        this.ceo = ceo;
        this.bankAccountsArray = new ArrayList<>();
    }

    public String getBankName() {
        return bankName;
    }

    public ArrayList<BankAccount> getBankAccountsArray() {
        return bankAccountsArray;
    }


    public void addBankAccount(BankAccount bankAccount) {
        bankAccountsArray.add(bankAccount);
    }

    public void removeBankAccount(BankAccount bankAccount) {
        bankAccountsArray.remove(bankAccount);
    }

    public void removeAllBankAccountsForAUser(User user) {
        bankAccountsArray.removeIf(ba -> ba.getUser().equals(user));
    }

    public ArrayList<BankAccount> getAllBankAccountsForAUser(User user) {
        ArrayList<BankAccount> bankAccounts = new ArrayList<>();
        for (BankAccount bankAccount : bankAccountsArray) {
            if (bankAccount.getUser().equals(user)) {
                bankAccounts.add(bankAccount);
            }
        }

        return bankAccounts;
    }

    public String getAllBankAccountsTableByUser(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-------------------------------------------------\n");
        stringBuilder.append("| index |       number        | alias | balance | currency |\n");

        for (int i = 0; i < bankAccountsArray.stream().filter(bankAccount -> user.equals(bankAccount.getUser())).toArray().length; i++) {
            stringBuilder.append("|   ").append(i);
            stringBuilder.append("   | ").append(bankAccountsArray.get(i).getAccountNumber());
            stringBuilder.append(" | ").append(bankAccountsArray.get(i).getAccountAlias());
            stringBuilder.append(" | ").append(bankAccountsArray.get(i).getBalance());
            stringBuilder.append(" | ").append(bankAccountsArray.get(i).getCurrency().toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
