public class Transaction {
    private final String accountFrom;
    private final String accountTo;
    private final String category;

    private final double value;

    public Transaction(String accountFrom, String accountTo, String category, double value) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder
                .append(" | ").append(accountFrom)
                .append(" | ").append(accountTo)
                .append(" | ").append(category)
                .append(" | ").append(value).append(" |").toString();
    }
}
