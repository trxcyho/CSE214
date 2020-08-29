/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #6
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * Grocery Driver acts as the main driver for the inventory management system
 */

import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;

import org.json.simple.parser.ParseException;

public class GroceryDriver extends HashedGrocery {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        HashedGrocery hashedGrocery;
        try {
            FileInputStream file = new FileInputStream("Grocery.obj");
            ObjectInputStream ois = new ObjectInputStream(file);
            hashedGrocery = (HashedGrocery) ois.readObject();
            ois.close();
            System.out.println("HashedGrocery is loaded from grocery.obj.\n");
        } catch (IOException | ClassNotFoundException e) {
            hashedGrocery = new HashedGrocery();
            System.out.println("Grocery.obj does not exist. Creating new" +
              " HashedGrocery object...\n");
        }
        System.out.println("Business Day " + hashedGrocery.getBusinessDay());
        while (!exit) {
            try {
                System.out.println("Menu :\n\n" +
                        "(L) Load item catalog\n" +
                        "(A) Add items\n" +
                        "(B) Process Sales\n" +
                        "(C) Display all items\n" +
                        "(N) Move to next business day\n" +
                        "(Q) Quit");
                System.out.println("\nEnter option: ");
                String x = scan.nextLine();
                x = x.toLowerCase();
                switch (x) {
                    case "a":
                        System.out.println("Enter item code: ");
                        String code = scan.nextLine();
                        System.out.println("Enter item name: ");
                        String name = scan.nextLine();
                        System.out.println("Enter Quantity in store: ");
                        int quantity = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Enter Average sales per day: ");
                        int averageSales = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Enter price: ");
                        double price = scan.nextDouble();
                        scan.nextLine();
                        Item newItem = new Item(code, name, quantity, averageSales, price);
                        hashedGrocery.addItem(newItem);
                        break;
                    case "l":
                        System.out.println("Enter file to load: ");
                        String file = scan.nextLine();
                        hashedGrocery.addItemCatalog(file);
                        break;
                    case "b":
                        System.out.println("Enter filename: ");
                        String filename = scan.nextLine();
                        hashedGrocery.processSales(filename);
                        break;
                    case "c":
                        System.out.println(hashedGrocery.toString());
                        break;
                    case "n":
                        hashedGrocery.nextBusinessDay();
                        break;
                    case "q":
                        try {
                            FileOutputStream stream = new FileOutputStream("Grocery.obj");
                            ObjectOutputStream output = new ObjectOutputStream(stream);
                            output.writeObject(hashedGrocery);
                            output.close();
                            System.out.println("HashedGrocery is saved in grocery.obj.");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        System.out.println("Program terminating normally " +
                                "...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            } catch (IllegalArgumentException | InputMismatchException | ParseException | IOException ex) {
                System.out.println(ex);
            }
        }
    }
}
