/**
 * General Ledger Manager class extends General Ledger class and includes
 * the main method
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #1
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class GeneralLedgerManager extends GeneralLedger {
    public static void main(String[] args) throws InvalidTransactionException,
            FullGeneralLedgerException, TransactionAlreadyExistsException,
            InvalidLedgerPositionException {
        Scanner scan = new Scanner(System.in);
        GeneralLedger generalLedger = new GeneralLedger();
        GeneralLedger generalLedgerBackup = new GeneralLedger();
        boolean exit = false;

        while (!exit) {
            try {
                System.out.println("(A) Add Transaction \n(G) Get Transaction" +
                        "\n(R) Remove Transaction \n(P) Print Transactions in " +
                        "General Ledger \n(F) Filter by Date \n(L) Look for " +
                        "Transaction \n(S) Size \n(B) Backup \n(PB) Print " +
                        "Transactions in Backup \n(RB) Revert to Backup \n(CB) " +
                        "Compare Backup with Current \n(PF) Print Financial " +
                        "Information \n(Q) Quit");
                System.out.println("\nEnter a selection: ");
                String x = scan.nextLine();
                x = x.toLowerCase();
                switch (x) {
                    case "a":
                        System.out.println("Enter Date: ");
                        String date = scan.nextLine();
                        System.out.println("Enter Amount ($): ");
                        double amount = scan.nextDouble();
                        System.out.println("Enter Description: ");
                        scan.nextLine();
                        String description = scan.nextLine();
                        Transaction add = new Transaction(date, amount,
                                description);
                        generalLedger.addTransaction(add);
                        System.out.println("Transaction successfully added to" +
                                " the ledger.");
                        break;
                    case "g":
                        System.out.println("\nEnter position: ");
                        int position = scan.nextInt();
                        scan.nextLine();
                        generalLedger.printTableHeading();
                        Transaction location = generalLedger.getTransaction
                                (position);
                        generalLedger.printTransaction(location, position);
                        break;
                    case "r":
                        System.out.println("\nEnter position: ");
                        int positionToRemove = scan.nextInt();
                        scan.nextLine();
                        generalLedger.removeTransaction(positionToRemove);
                        System.out.println("Transaction has been successfully" +
                                " removed from the ledger.");
                        break;
                    case "p":
                        generalLedger.printAllTransactions();
                        break;
                    case "f":
                        System.out.println("Enter Date: ");
                        String filterDate = scan.nextLine();
                        filter(generalLedger, filterDate);
                        break;
                    case "l":
                        System.out.println("Enter a Date: ");
                        String dateToFind = scan.nextLine();
                        System.out.println("Enter Amount($): ");
                        double amountToFind = scan.nextDouble();
                        scan.nextLine();
                        System.out.println("Enter Description: ");
                        String descriptionToFind = scan.nextLine();
                        Transaction lookFor = new Transaction(dateToFind,
                                amountToFind, descriptionToFind);
                        int tracker = 0;
                        for (int i = 1; i < generalLedger.
                                getAmountOfTransactions(); i++) {
                            if (lookFor.equals(generalLedger.getLedger()[i]))
                                tracker = i;
                        }
                        if (tracker != 0) {
                            generalLedger.printTableHeading();
                            generalLedger.printTransaction(generalLedger.
                                    getLedger()[tracker], tracker);
                        } else
                            System.out.println("Transaction does NOT exist.");
                        break;
                    case "s":
                        System.out.println("There are " + generalLedger.size() +
                                " transactions currently in the general ledger.");
                        break;
                    case "b":
                        generalLedgerBackup = (GeneralLedger) generalLedger.
                                clone();
                        System.out.println("Created a backup of the current " +
                                "general ledger.");
                        break;
                    case "pb":
                        generalLedgerBackup.printAllTransactions();
                        break;
                    case "rb":
                        if (generalLedgerBackup != null)
                            generalLedger = (GeneralLedger) generalLedgerBackup.
                                    clone();
                        else
                            System.out.println("Backup does not exist.");
                        break;
                    case "cb":
                        if (generalLedger.getAmountOfTransactions() !=
                                generalLedgerBackup.getAmountOfTransactions())
                            System.out.println("The current general ledger is" +
                                    " NOT the same as the backup copy.");
                        else {
                            for (int i = 0; i < generalLedger.
                                    getAmountOfTransactions() - 1; i++) {
                                if (generalLedger.getLedger()[i] !=
                                        generalLedgerBackup.getLedger()[i]) {
                                    System.out.println("The current general " +
                                            "ledger is NOT the same as the backup " +
                                            "copy.");
                                    break;
                                } else
                                    System.out.println("The current general " +
                                            "ledger is the same as the backup copy.");
                            }
                        }
                        break;
                    case "pf":
                        System.out.println("Financial Data for Jack's Account");
                        System.out.println("Assets: $" + generalLedger.
                                getTotalDebitAmount() + "\nLiabilities: $" +
                                generalLedger.getTotalCreditAmount());
                        System.out.println("Net Worth: $" + (generalLedger.
                                getTotalDebitAmount() - generalLedger.
                                getTotalCreditAmount()) + "\n");
                        break;
                    case "q":
                        System.out.println("Program terminating successfully " +
                                "...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            } catch (InvalidTransactionException |
                    TransactionAlreadyExistsException | IllegalArgumentException |
                    FullGeneralLedgerException | InvalidLedgerPositionException |
                    InputMismatchException ex) {
                System.out.println(ex.toString() + "\n");
            }

        }
    }
}
