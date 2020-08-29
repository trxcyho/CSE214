/**
 * General Ledger class extends transaction class and creates a object that will
 * hold an array of transactions
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #1
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */
public class GeneralLedger extends Transaction {
    public static final int MAX_TRANSACTIONS = 50;
    private Transaction[] ledger;
    private double totalDebitAmount, totalCreditAmount;
    private int amountOfTransactions;

    /**
     * Creates a default constructor that initializes the array of transactions(
     * ledger) to hold 51 transactions and sets the amount of transactions equal
     * to one. Total debit amount and total credit amount is set to 0.
     */
    public GeneralLedger() {
        ledger = new Transaction[MAX_TRANSACTIONS + 1];
        amountOfTransactions = 1;
        totalCreditAmount = 0;
        totalDebitAmount = 0;
    }

    public Transaction[] getLedger() {
        return ledger;
    }

    public void setLedger(Transaction[] ledger) {
        this.ledger = ledger;
    }

    public double getTotalDebitAmount() {
        return totalDebitAmount;
    }

    public void setTotalDebitAmount(double totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    public double getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public void setTotalCreditAmount(double totalCreditAmount) {
        this.totalCreditAmount = totalCreditAmount;
    }

    public int getAmountOfTransactions() {
        return amountOfTransactions;
    }

    public void setAmountOfTransactions(int amountOfTransactions) {
        this.amountOfTransactions = amountOfTransactions;
    }

    /**
     * Adds a Transaction to the ledger and orders the transactions in the
     * ledger by date
     *
     * @param newTransaction The Transaction object that is to be added to the
     *   ledger
     * @throws FullGeneralLedgerException Ledger has 50 Transactions already
     * @throws InvalidTransactionException Transaction being added doesn't have
     *   an amount, valid date, or a description
     * @throws TransactionAlreadyExistsException Inside the ledger, there is a
     *   transaction that has the exact same date, amount, and description
     */
    public void addTransaction(Transaction newTransaction) throws
      FullGeneralLedgerException, InvalidTransactionException,
      TransactionAlreadyExistsException {
        if (amountOfTransactions > MAX_TRANSACTIONS) {
            throw new FullGeneralLedgerException("Ledger is full.");
        }
        if (newTransaction.getAmount() == 0 || !isValidDate(newTransaction.
          getDate()) || newTransaction.getDescription().equals("")) {
            throw new InvalidTransactionException("Transaction is invalid.");
        }
        if (exists(newTransaction))
            throw new TransactionAlreadyExistsException("Transaction already " +
              "exists.");

        if (newTransaction.getAmount() > 0)
            totalDebitAmount += newTransaction.getAmount();
        else
            totalCreditAmount += -newTransaction.getAmount();
        int tracker = 0;
        if (ledger[1] == null) {
            ledger[amountOfTransactions] = newTransaction;
        } else {
            for (int i = 1; i < amountOfTransactions; i++) {
                if (compareDate(newTransaction.getDate(), ledger[i].getDate()) <
                  0) {
                    tracker = i;
                    break;
                }
                if (compareDate(newTransaction.getDate(), ledger[i].getDate())
                  == 0) {
                    tracker = i + 1;
                    break;
                }
            }
            if (tracker != 0) {
                for (int i = tracker; i <= amountOfTransactions; i++) {
                    Transaction temp = ledger[i];
                    ledger[i] = newTransaction;
                    newTransaction = temp;
                }
            } else {
                ledger[amountOfTransactions] = newTransaction;
            }
        }
        amountOfTransactions++;
    }

    /**
     * Removes a transaction from a certain position from the ledger
     *
     * @param position The position of the Transaction that is to be removed
     * @throws InvalidLedgerPositionException Exception occurs if there is no
     *   transaction at that position
     */
    public void removeTransaction(int position) throws
      InvalidLedgerPositionException {
        if (position >= amountOfTransactions || position <= 0)
            throw new InvalidLedgerPositionException("Position is not valid");
        if (ledger[position].getAmount() > 0)
            totalDebitAmount -= ledger[position].getAmount();
        else
            totalCreditAmount += ledger[position].getAmount();
        if (position != amountOfTransactions - 1) {
            for (int i = position; i < amountOfTransactions; i++)
                ledger[i] = ledger[i + 1];
        }
        amountOfTransactions--;
    }

    /**
     * Gets the transaction at a certain position in the ledger
     *
     * @param position The position of the transaction the user wants
     * @return The transaction at that position in the ledger
     * @throws InvalidLedgerPositionException Occurs if there is no transaction
     *   at that position in the ledger
     */
    public Transaction getTransaction(int position) throws
      InvalidLedgerPositionException {
        if (position >= amountOfTransactions)
            throw new InvalidLedgerPositionException("Position is not valid");
        return ledger[position];
    }

    /**
     * Filters through the general ledger and print transactions with the
     * specified date
     *
     * @param generalLedger The ledger which consists of all the transactions to
     *   filter through
     * @param date The date of the transaction that the user wants to find
     *   within the ledger
     */
    public static void filter(GeneralLedger generalLedger, String date) {
        generalLedger.printTableHeading();
        for (int i = 1; i < generalLedger.getAmountOfTransactions(); i++)
            if (generalLedger.getLedger()[i].getDate().equals(date))
                generalLedger.printTransaction(generalLedger.getLedger()[i], i);
    }

    /**
     * Makes a deep copy of the ledger
     *
     * @return Deep copy of the ledger which has all of the ledger's contents
     */
    public Object clone() {
        GeneralLedger cloneOfLedger = new GeneralLedger();
        for (int i = 1; i < amountOfTransactions; i++) {
            Transaction t = getLedger()[i];
            Transaction newTransaction = (Transaction) t.clone();
            cloneOfLedger.getLedger()[i] = newTransaction;
        }
        cloneOfLedger.setTotalDebitAmount(totalDebitAmount);
        cloneOfLedger.setTotalCreditAmount(totalCreditAmount);
        cloneOfLedger.setAmountOfTransactions(amountOfTransactions);
        return cloneOfLedger;
    }

    /**
     * Checks if there already exists the same transaction in the ledger
     *
     * @param transaction The transaction that needs to be checked if it exists
     *   already in the ledger
     * @return True if the transaction already exists in the ledger, false if it
     *   doesn't exist
     * @throws IllegalArgumentException Exception is thrown if transaction is a
     *   valid transaction object
     */
    public boolean exists(Transaction transaction) throws
      IllegalArgumentException {
        if (transaction instanceof Transaction) {
            for (int i = 1; i < amountOfTransactions; i++) {
                if (transaction.equals(ledger[i])) {
                    return true;
                }
            }
            return false;
        } else
            throw new IllegalArgumentException("Transaction is not a valid " +
              "transaction object.");
    }

    /**
     * Gives the current amount of transactions in the ledger
     *
     * @return the number of transactions in the ledger
     */
    public int size() {
        return getAmountOfTransactions() - 1;
    }

    /**
     * Prints all the transactions in the ledger
     */
    public void printAllTransactions() {
        this.printTableHeading();
        System.out.println(this.toString());
    }

    /**
     * Turns all transaction objects into a string
     *
     * @return A string of all transactions within the ledger
     */
    public String toString() {
        String transaction = "";
        for (int j = 1; j <= amountOfTransactions - 1; j++) {
            if (ledger[j].getAmount() > 0)
                transaction = transaction + "\n" + String.format("%-7d%-14s%" +
                  "-9s%-10s%-60s", j, ledger[j].getDate(), ledger[j].getAmount()
                  , "", ledger[j].getDescription());
            else
                transaction = transaction + "\n" + String.format("%-7d%-14s%" +
                  "-9s%-10s%-60s", j, ledger[j].getDate(), "", -ledger[j].
                  getAmount(), ledger[j].getDescription());
        }
        return transaction;
    }

    /**
     * Prints the table heading of transactions(No., Date, Debit, Credit, and
     * Description)
     */
    public void printTableHeading() {
        System.out.println(String.format("%-7s%-14s%-9s%-10s%-60s", "No.",
          "Date", "Debit", "Credit", "Description"));
        for (int i = 0; i < 100; i++)
            System.out.print("-");
    }

    /**
     * Prints the transaction at a certain position in the ledger
     *
     * @param transaction The transaction that is to be printed
     * @param position    The position of the transaction in the ledger
     */
    public void printTransaction(Transaction transaction, int position) {
        if (ledger[position].getAmount() > 0)
            System.out.println("\n" + String.format("%-7d%-14s%-9s%-10s%-60s",
              position, transaction.getDate(), transaction.getAmount(), "",
              transaction.getDescription()));
        else
            System.out.println("\n" + String.format("%-7d%-14s%-9s%-10s%-60s",
              position, transaction.getDate(), "", -transaction.getAmount(),
              transaction.getDescription()));
    }

    /**
     * Checks if the date is valid
     *
     * @param date The date that is to be checked if valid
     * @return True if the date is within certain years, months, and days.
     *   False otherwise
     */
    public boolean isValidDate(String date) {
        int count = 0;
        for (int i = 0; i < date.length(); i++) {
            if (date.charAt(i) == '/')
                count++;
        }
        if (count != 2)
            return false;
        String[] arrOfDate = date.split("/");
        if (arrOfDate.length != 3)
            return false;
        if (Integer.parseInt(arrOfDate[0]) >= 1990 && Integer.
          parseInt(arrOfDate[0]) <= 2050) {
            if (Integer.parseInt(arrOfDate[1]) >= 1 && Integer.
              parseInt(arrOfDate[1]) <= 12) {
                return Integer.parseInt(arrOfDate[2]) >= 1 && Integer.
                  parseInt(arrOfDate[2]) <= 30;
            }
        }
        return false;
    }

    /**
     * Compares two dates to see which is older or if they are the same date
     *
     * @param date1 The first date to be compared
     * @param date2 The second date to be compared
     * @return Positive if second date is older than the first date, negative
     *   otherwise. Zero if the two dates are the same
     */
    public int compareDate(String date1, String date2) {
        String[] splitDate1 = date1.split("/");
        String[] splitDate2 = date2.split("/");
        if (Integer.parseInt(splitDate1[0]) < Integer.parseInt(splitDate2[0]))
            return -1;
        if (Integer.parseInt(splitDate1[0]) > Integer.parseInt(splitDate2[0]))
            return 1;
        if (Integer.parseInt(splitDate1[0]) == Integer.parseInt(splitDate2[0]))
        {
            if (Integer.parseInt(splitDate1[1]) < Integer.parseInt(
              splitDate2[1]))
                return -1;
            if (Integer.parseInt(splitDate1[1]) > Integer.parseInt(
              splitDate2[1]))
                return 1;
            if (Integer.parseInt(splitDate1[1]) == Integer.parseInt(
              splitDate2[1])) {
                if (Integer.parseInt(splitDate1[2]) < Integer.parseInt(
                  splitDate2[2]))
                    return -1;
                if (Integer.parseInt(splitDate1[2]) > Integer.parseInt(
                  splitDate2[2]))
                    return 1;
                if (Integer.parseInt(splitDate1[2]) == Integer.parseInt(
                  splitDate2[2]))
                    return 0;
            }
        }
        return 0;
    }
}
