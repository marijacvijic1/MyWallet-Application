import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.jline.JLineTextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

import java.util.*;


public class MainMenu {

    private TextIO textIO;
    private User user = new User();
    private final Bank bank = new Bank("FreeMoneyBank", "Resavska 24", "John Doe");

    public void run() {
        textIO = TextIoFactory.getTextIO();

        runMainMenu(textIO.getTextTerminal());
    }

    private void runMainMenu(TextTerminal terminal) {
        terminal.print("==== Welcome to " + bank.getBankName() + " Main Menu ====" +
                "\n Enter the number of the desired option:" +
                "\n 1. Register an account: " +
                "\n 2. Login: " +
                "\n 0. Exit \n");

        String[] validOptions = {"0", "1", "2"};
        boolean found;
        String option;

        do {
            option = textIO.newStringInputReader().read("option:");
            found = Arrays.asList(validOptions).contains(option);

            if (!found) {
                terminal.println("Unrecognized command! Try again.");
            }
        } while (!found);

        switch (option) {
            case "1":
                runRegisterMenu(terminal);
                break;
            case "2":
                runLoginMenu(terminal);
                break;
            case "0":
                exitApp(terminal, option);
                break;
            default:
                terminal.println("Unrecognized command!");
                break;
        }
    }

    private void clearScreen(TextTerminal terminal) {
        if (terminal instanceof JLineTextTerminal) {
            terminal.print("\033[H\033[2J");
        } else if (terminal instanceof SwingTextTerminal) {
            ((SwingTextTerminal) terminal).resetToOffset(0);
        }
    }

    private void runRegisterMenu(TextTerminal terminal) {
        clearScreen(terminal);

        terminal.print("==== Welcome to MojNovcanik Registration Menu ====\n");

        String userName = textIO.newStringInputReader().read("Username");
        String password = textIO.newStringInputReader().withMinLength(6).withInputMasking(true).read("Password");
        int age = textIO.newIntInputReader().withMinVal(13).read("Age");
        // TODO: Bolji regex, ovaj ne radi nista
        String email = textIO.newStringInputReader().withPattern("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").read("Email");

        user = new User(userName, password, password, age);
        terminal.print("==== Account successfully created!\n");

        // TODO: Ovde treba da vrati na login/register menu ne na main menu

        escapeMenu(terminal);
    }

    private void exitApp(TextTerminal terminal, String userOption) {
        terminal.println("Exiting app..");

        if (userOption.equals(("0"))) {
            InternalUtil.sleep(2);

            textIO.dispose();
        }
    }

    private void runLoginMenu(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.print("==== Welcome to " + bank.getBankName() + " Login Menu ====\n");
        boolean isSuccessfulLogin = false;

        do {
            String username = textIO.newStringInputReader().read("Username");
            String password = textIO.newStringInputReader().withMinLength(6).withInputMasking(true).read("Password");

            try {
                validateUserLoginData(username, password);
                isSuccessfulLogin = true;

                terminal.println("Successfully logged in!");
                InternalUtil.sleep(2);

                runBankMenu(terminal);
            } catch (Exception e) {
                terminal.println(e.getMessage());

                if (e.getMessage().equalsIgnoreCase("User not found in the system. Try to register first")) {
                    isSuccessfulLogin = true; // using to escape the while loop and redirect the user to registration form.

                    terminal.println("Redirecting to the registration form..");
                    InternalUtil.sleep(2);

                    runRegisterMenu(terminal);
                }
            }

        } while (!isSuccessfulLogin);
    }

    private void validateUserLoginData(String username, String password) throws RuntimeException {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new RuntimeException("User not found in the system. Try to register first");
        }

        if (!username.equalsIgnoreCase(user.getUsername()) || !password.equalsIgnoreCase(user.getPassword())) {
            throw new RuntimeException("Username and password not found. Try again!");
        }
    }

    private void runBankMenu(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.print("==== Bank Account Menu ====" +
                "\n Enter the number of the desired option:" +
                "\n 1. Create new account" +
                "\n 2. Delete existing account" +
                "\n 3. List all accounts" +
                "\n 4. Make a payment" +
                "\n 5. List all transactions" +
                "\n 6. Bonus payment (bank lends out 50 000 dinars)" +
                "\n 7. Withdrawal from an account" +
                "\n 8. Change currency on account" +
                "\n 9. Show transactions by category" +
                "\n 0. Exit \n");

        String[] validOptions = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        boolean found;
        String option;

        do {
            option = textIO.newStringInputReader().read("option:");
            found = Arrays.asList(validOptions).contains(option);

            if (!found) {
                terminal.println("Unrecognized command! Try again.");
            }
        } while (!found);

        switch (option) {
            case "1":
                createNewAccountMenu(terminal);
                break;
            case "2":
                deleteAccountMenu(terminal);
                break;
            case "3":
                showBankAccountsMenu(terminal);
                break;
            case "4":
                paymentMenu(terminal);
                break;
            case "5":
                listAllTransactionsMenu(terminal);
                break;
            case "6":
                makeBonusPaymentMenu(terminal);
                break;
            case "7":
                withdrawalMenu(terminal);
                break;
            case "8":
                changeCurrencyMenu(terminal);
                break;
            case "9":
                listTransactionsCategoriesMenu(terminal);
                break;
            case "0":
                exitApp(terminal, option);
                break;
            default:
                terminal.println("Unrecognized command!");
                break;
        }
    }

    private void createNewAccountMenu(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.print("==== Create new Bank Account Menu ====\n" +
                "Enter the number of the desired option:" +
                "\n 1. New Account Form \n" +
                "\n 2. Back to the previous menu" +
                "\n 0. Exit \n");

        String[] validOptions = {"0", "1", "2"};
        boolean found;
        String option;

        do {
            option = textIO.newStringInputReader().read("option:");
            found = Arrays.asList(validOptions).contains(option);

            if (!found) {
                terminal.println("Unrecognized command! Try again.");
            }
        } while (!found);

        switch (option) {
            case "1":
                createNewAccountForm(terminal);
                break;
            case "2":
                runBankMenu(terminal);
                break;
            case "0":
                exitApp(terminal, option);
                break;
            default:
                terminal.println("Unrecognized command!");
                break;
        }

    }

    private void createNewAccountForm(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.println("==== Create new Bank Account Form ====\n");

        String accountAlias = textIO.newStringInputReader()
                .read("Account Alias");

        BankAccount bankAccount = new BankAccount(user, accountAlias);
        bank.addBankAccount(bankAccount);

        terminal.println("Bank Account successfully created!\n");
        escapeMenu(terminal);
    }

    private void deleteAccountMenu(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.print("==== Delete Bank Account Menu ====\n" +
                "Enter the index of account you want to delete: \n");

        ArrayList<BankAccount> bankAccounts = bank.getAllBankAccountsForAUser(user);

        terminal.print(bank.getAllBankAccountsTableByUser(user));

        int deleteBankAccount = textIO.newIntInputReader().read("option:");
        bank.removeBankAccount(bankAccounts.get(deleteBankAccount));

        terminal.println("Bank Account successfully deleted!\n");
        escapeMenu(terminal);
    }

    private void makeBonusPaymentMenu(TextTerminal terminal) {
        ArrayList<BankAccount> bankAccounts = bank.getAllBankAccountsForAUser(user);

        if (bankAccounts.get(0).isBonusPaymentPayedOut()) {
            terminal.println("\nSorry bonus payment can only be payed once!");
            InternalUtil.sleep(2);

            runBankMenu(terminal);
            return;
        }

        bankAccounts.get(0).setBalance(50000);
        bankAccounts.get(0).setBonusPaymentPayedOut(true);

        terminal.println("\n\n Bonus payment sent! Returning to main menu.");

        InternalUtil.sleep(2);
        runBankMenu(terminal);
    }

    private void withdrawalMenu(TextTerminal terminal) {
        ArrayList<BankAccount> bankAccounts = bank.getAllBankAccountsForAUser(user);
        if (bankAccounts.size() == 0) {
            terminal.println("No accounts found! Going back to bank menu..\n");
            InternalUtil.sleep(2);
            runBankMenu(terminal);
        }

        clearScreen(terminal);
        terminal.print("==== Bank Accounts withdrawal Menu ====\nEnter the index of account from which you want to make withdrawal: \n");

        terminal.print(bank.getAllBankAccountsTableByUser(user));
        boolean isSuccessfulWithdrawal = false;

        do {
            try {
                int accountIndex = textIO.newIntInputReader().read("account index:");
                double withdrawalAmount = textIO.newDoubleInputReader().read("withdrawal amount:");

                bankAccounts.get(accountIndex).withdrawal(withdrawalAmount);
                terminal.println("Withdrawal was successful!\n");
                isSuccessfulWithdrawal = true;
            } catch (Exception e) {
                terminal.println(e.getMessage() + " Try again!");
            }
        } while (!isSuccessfulWithdrawal);

        escapeMenu(terminal);
    }

    private void showBankAccountsMenu(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.print("==== Bank Accounts List Menu ====\n");

        ArrayList<BankAccount> bankAccounts = bank.getAllBankAccountsForAUser(user);

        terminal.print(bank.getAllBankAccountsTableByUser(user));

        terminal.print("Enter the number of the desired option:" +
                "\n 1. Back \n" +
                "\n 0. Exit \n");

        String option = textIO.newStringInputReader().read();

        switch (option) {
            case "1":
                runBankMenu(terminal);
                break;
            case "0":
                exitApp(terminal, option);
                break;
            default:
                terminal.println("Unrecognized command!");
                break;
        }
    }

    private void paymentMenu(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.print("==== Make a Payment Menu ====\n");

        ArrayList<BankAccount> bankAccounts = bank.getAllBankAccountsForAUser(user);
        if (bankAccounts.size() == 0) {
            terminal.println("No accounts found! Going back to bank menu..\n");
            InternalUtil.sleep(2);
            runBankMenu(terminal);
        }

        terminal.print(bank.getAllBankAccountsTableByUser(user));
        boolean isSuccessfulExternalPayment = false;

        do {
            try {
                int accountIndex = textIO.newIntInputReader().read("account index:");
                double withdrawalAmount = textIO.newDoubleInputReader().read("payment amount:");
                String externalAccount = textIO.newStringInputReader().read("external account:");
                String transactionCategory = textIO.newStringInputReader().read("transaction category (food, electronics etc.):");

                bankAccounts.get(accountIndex).makeExternalPayment(withdrawalAmount, externalAccount, transactionCategory);
                terminal.println("Payment was successful!\n");
                isSuccessfulExternalPayment = true;
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                terminal.println(e.getMessage() + " Try again!");
            }
        } while (!isSuccessfulExternalPayment);

        escapeMenu(terminal);
    }

    private void listAllTransactionsMenu(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.print("==== List all transactions Menu ====\n");

        ArrayList<BankAccount> bankAccounts = bank.getAllBankAccountsForAUser(user);

        terminal.print(buildTransactionsString(bankAccounts) + "\n");

        escapeMenu(terminal);
    }

    private void listTransactionsCategoriesMenu(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.print("==== Select transactions category Menu ====\n");

        ArrayList<BankAccount> bankAccounts = bank.getAllBankAccountsForAUser(user);

        HashMap<String, List<Transaction>> transactionsCategoryHashMap = buildTransactionsCategoryHashMap(bankAccounts);

        StringBuilder categoryOptions = new StringBuilder();
        for (String tCategory : transactionsCategoryHashMap.keySet()) {
            categoryOptions.append(transactionsCategoryHashMap.get(tCategory).get(0).getCategory()).append("\n");
        }

        terminal.print(categoryOptions.toString());

        String tCategory = textIO.newStringInputReader().read("category:");
        listTransactionsByCategoryMenu(terminal, tCategory, transactionsCategoryHashMap);
    }

    private void listTransactionsByCategoryMenu(TextTerminal terminal, String tCategory, HashMap<String, List<Transaction>> transactionsCategoryHashMap) {
        clearScreen(terminal);
        terminal.print("==== Transactions by "+ tCategory +" Menu ====\n");

        List<Transaction> transactionsByCategory = transactionsCategoryHashMap.get(tCategory);

        terminal.print(buildTransactionsByCategoryString(transactionsByCategory));
        escapeMenu(terminal);
    }

    private HashMap<String, List<Transaction>> buildTransactionsCategoryHashMap(ArrayList<BankAccount> bankAccounts) {
        HashMap<String, List<Transaction>> transactionsCategoryHashMap = new HashMap<>();

        for (BankAccount bankAccount : bankAccounts) {
            for (Transaction transaction : bankAccount.getTransactionsList()) {
                if (!transactionsCategoryHashMap.containsKey(transaction.getCategory())) {
                    List<Transaction> list = new ArrayList<>();
                    list.add(transaction);

                    transactionsCategoryHashMap.put(transaction.getCategory(), list);
                } else {
                    transactionsCategoryHashMap.get(transaction.getCategory()).add(transaction);
                }
            }
        }

        return transactionsCategoryHashMap;
    }

    private String buildTransactionsByCategoryString(List<Transaction> transactions) {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------------------------------\n");
        sb.append("| index |       account from        | account to |   category   | amount |\n");
        int i = 0;
        for (Transaction t  : transactions) {
            sb.append("| ").append(i).append(t.toString()).append("\n");
            i++;
        }

        return sb.toString();
    }

    private String buildTransactionsString(ArrayList<BankAccount> bankAccounts) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--------------------------------------------------------------------------\n");
        stringBuilder.append("| index |       account from        | account to |   category   | amount |\n");
        for (int i = 0; i < bankAccounts.size(); i++) {
            for (Transaction t : bankAccounts.get(i).getTransactionsList()) {
                stringBuilder.append("| ").append(i).append(t.toString()).append("\n");
                i++;
            }
        }

        return stringBuilder.toString();
    }

    private void escapeMenu(TextTerminal terminal) {
        terminal.print("Enter the number of the desired option:" +
                "\n 1. Back \n" +
                "\n 0. Exit \n");

        String[] validOptions = {"0", "1"};
        boolean found;
        String option;

        do {
            option = textIO.newStringInputReader().read("option:");
            found = Arrays.asList(validOptions).contains(option);

            if (!found) {
                terminal.println("Unrecognized command! Try again.");
            }
        } while (!found);

        switch (option) {
            case "1":
                runBankMenu(terminal);
                break;
            case "0":
                exitApp(terminal, option);
                break;
            default:
                terminal.println("Unrecognized command!");
                break;
        }
    }

    private void changeCurrencyMenu(TextTerminal terminal) {
        clearScreen(terminal);
        terminal.print("==== Change currency Menu ====\nEnter the index of account you want to change the currency: \n");

        ArrayList<BankAccount> bankAccounts = bank.getAllBankAccountsForAUser(user);

        terminal.print(bank.getAllBankAccountsTableByUser(user));

        int bankAccountIndex = textIO.newIntInputReader().read("account:");
        CurrencyEnum currency = textIO.newEnumInputReader(CurrencyEnum.class)
                .read("In which currency do you want to change?");

        BankAccount bankAccount = bank.getBankAccountsArray().get(bankAccountIndex);
        boolean isSuccessfulCurrencyChange = false;

        do {
            try {
                bankAccount.setNewCurrencyBalance(bankAccount.getBalance(), currency);
                terminal.println("Currency change was successful!\n");
                isSuccessfulCurrencyChange = true;
            } catch (RuntimeException e) {
                terminal.println("Unsuccessful currency change! " + e.getMessage());
            }
        } while (!isSuccessfulCurrencyChange);

        escapeMenu(terminal);
    }
}


