/**
 * Transaction class generates transaction objects with a date, amount, and description.
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #1
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */
public class Transaction {
    private String date, description; // the date and the description of the transaction
    private double amount;  //the amount of the transaction(positive for debit and negative for credit)

    public Transaction() {
    } //default constructor to create an instance of transaction

    /**
     * Custom constructor
     * Creates and initializes the instance of Transaction
     *
     * @param date        The date of the transaction occurred
     * @param amount      Represents the amount of the transaction
     * @param description What the transaction was for
     */
    public Transaction(String date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Deep copies the transaction object
     *
     * @return A transaction object with same contents but different references
     */
    public Object clone() {
        Transaction t = new Transaction();
        t.date = this.getDate();
        t.description = this.getDescription();
        t.amount = this.getAmount();
        return t;
    }

    /**
     * Checks if contents of two transactions are equal to each other
     *
     * @param obj The object that is being compared
     * @return True if contents are equal and false if contents are not
     */
    public boolean equals(Object obj) {
        if (obj instanceof Transaction)
            return this.getDate().equals(((Transaction) obj).getDate()) &&
              this.getDescription().equals(((Transaction) obj).getDescription())
              && this.getAmount() == ((Transaction) obj).getAmount();
        return false;
    }
}
