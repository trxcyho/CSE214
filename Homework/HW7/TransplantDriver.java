/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #7
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * TransplantDriver acts as the main driver for the TransplantGraph application
 */

import java.io.*;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TransplantDriver extends TransplantGraph {
    public static final String DONOR_FILE = "donors.txt";
    public static final String RECIPIENT_FILE = "recipients.txt";

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        //first serialize
        TransplantGraph transplantGraph;
        try {
            FileInputStream file = new FileInputStream("transplant.obj");
            ObjectInputStream ois = new ObjectInputStream(file);
            transplantGraph = (TransplantGraph) ois.readObject();
            ois.close();
            System.out.println("Loading data from transplant.obj... \n");
        } catch (IOException | ClassNotFoundException e) {
            transplantGraph = buildFromFiles(DONOR_FILE, RECIPIENT_FILE);
            System.out.println("transplant.obj not found. Creating new " +
              "TransplantGraph object...");
            System.out.println("Loading data from 'donors.txt'...");
            System.out.println("Loading data from 'recipients.txt'...\n");
        }
        while (!exit) {
            try {
                System.out.println("\nMenu:\n" +
                        "    (LR) - List all recipients\n" +
                        "    (LO) - List all donors\n" +
                        "    (AO) - Add new donor\n" +
                        "    (AR) - Add new recipient\n" +
                        "    (RO) - Remove donor\n" +
                        "    (RR) - Remove recipient\n" +
                        "    (SR) - Sort recipients\n" +
                        "    (SO) - Sort donors\n" +
                        "    (Q) - Quit");
                System.out.println("\nPlease select an option: ");
                String x = scan.nextLine();
                x = x.toLowerCase();
                switch (x) {
                    case "lr":
                        transplantGraph.printAllRecipients();
                        break;
                    case "lo":
                        transplantGraph.printAllDonors();
                        break;
                    case "ao":
                        System.out.println("Please enter the organ donor name: ");
                        String name = scan.nextLine();
                        System.out.println("Please enter the organs John Doe" +
                          " is donating: ");
                        String organ = scan.nextLine();
                        System.out.println("Please enter the blood type of John Doe: ");
                        String blood = scan.nextLine();
                        System.out.println("Please enter the age of John Doe: ");
                        int age = scan.nextInt();
                        scan.nextLine();
                        //create a patient
                        Patient patient = new Patient(name, organ, age, blood);
                        patient.setDonor(true);
                        //call add donor from transplant
                        transplantGraph.addDonor(patient);
                        System.out.println("The organ donor with ID " + patient.
                          getID() + " was successfully added to the donor list!");
                        break;
                    case "ar":
                        System.out.println("Please enter new recipient's name: ");
                        String name1 = scan.nextLine();
                        System.out.println("Please enter the recipient's blood type: ");
                        String blood1 = scan.nextLine();
                        System.out.println("Please enter the recipient's age: ");
                        int age1 = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Please enter the organ needed: ");
                        String organ1 = scan.nextLine();
                        //create a patient
                        Patient patient1 = new Patient(name1, organ1, age1, blood1);
                        patient1.setDonor(false);
                        //call add recipient from transplant
                        transplantGraph.addRecipient(patient1);
                        System.out.println(patient1.getName() + " is now on " +
                          "the organ transplant waitlist!");
                        break;
                    case "ro":
                        System.out.println("Please enter the name of the organ" +
                          " donor to remove: ");
                        String name2 = scan.nextLine();
                        transplantGraph.removeDonor(name2);
                        System.out.println(name2 + " was removed from the " +
                          "organ donor list.");
                        break;
                    case "rr":
                        System.out.println("Please enter the name of the " +
                          "recipient to remove: ");
                        String name3 = scan.nextLine();
                        transplantGraph.removeRecipient(name3);
                        System.out.println(name3 + " was removed from the " +
                          "organ transplant waitlist.");
                        break;
                    case "sr":
                        boolean sr = true;
                        transplantGraph.updateNumConnections();
                        while (sr) {
                            try {
                                System.out.println("\n    (I) Sort by ID\n" +
                                        "    (N) Sort by Number of Donors\n" +
                                        "    (B) Sort by Blood Type\n" +
                                        "    (O) Sort by Organ Needed\n" +
                                        "    (Q) Back to Main Menu");
                                String input = scan.nextLine();
                                input = input.toLowerCase();
                                switch (input) {
                                    case "i":
                                        Collections.sort(transplantGraph.getRecipients());
                                        transplantGraph.printAllRecipients();
                                        break;
                                    case "n":
                                        transplantGraph.getRecipients().sort(new
                                          NumConnectionsComparator());
                                        transplantGraph.printAllRecipients();
                                        break;
                                    case "b":
                                        transplantGraph.getRecipients().sort(new
                                          BloodTypeComparator());
                                        transplantGraph.printAllRecipients();
                                        break;
                                    case "o":
                                        transplantGraph.getRecipients().sort(new
                                          OrganComparator());
                                        transplantGraph.printAllRecipients();
                                        break;
                                    case "q":
                                        Collections.sort(transplantGraph.getRecipients());
                                        System.out.println("\nReturning to main menu.");
                                        sr = false;
                                        break;
                                    default:
                                        System.out.println("\nInvalid Input");
                                        break;
                                }
                            } catch (InputMismatchException | IllegalArgumentException e) {
                                System.out.println(e);
                            }
                        }
                        break;
                    case "so":
                        boolean so = true;
                        transplantGraph.updateNumConnections();
                        while (so) {
                            try {
                                System.out.println("\n    (I) Sort by ID\n" +
                                        "    (N) Sort by Number of Recipients\n" +
                                        "    (B) Sort by Blood Type\n" +
                                        "    (O) Sort by Organ Donated\n" +
                                        "    (Q) Back to Main Menu");
                                String input = scan.nextLine();
                                input = input.toLowerCase();
                                switch (input) {
                                    case "i":
                                        Collections.sort(transplantGraph.getDonors());
                                        transplantGraph.printAllDonors();
                                        break;
                                    case "n":
                                        transplantGraph.getDonors().sort(new
                                          NumConnectionsComparator());
                                        transplantGraph.printAllDonors();
                                        break;
                                    case "b":
                                        transplantGraph.getDonors().sort(new
                                          BloodTypeComparator());
                                        transplantGraph.printAllDonors();
                                        break;
                                    case "o":
                                        transplantGraph.getDonors().sort(new
                                          OrganComparator());
                                        transplantGraph.printAllDonors();
                                        break;
                                    case "q":
                                        Collections.sort(transplantGraph.getDonors());
                                        System.out.println("\nReturning to main menu.");
                                        so = false;
                                        break;
                                    default:
                                        System.out.println("\nInvalid Input");
                                        break;
                                }
                            } catch (InputMismatchException | IllegalArgumentException e) {
                                System.out.println(e);
                            }
                        }
                        break;
                    case "q":
                        //deserialization
                        try {
                            FileOutputStream stream = new FileOutputStream("transplant.obj");
                            ObjectOutputStream output = new ObjectOutputStream(stream);
                            output.writeObject(transplantGraph);
                            output.close();
                            System.out.println("\nWriting data to transplant.obj...");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        System.out.println("\nProgram terminating normally " +
                                "...");
                        exit = true;
                        break;
                    default:
                        System.out.println("\nInvalid Input");
                        break;
                }
            } catch (IllegalArgumentException | InputMismatchException ex) {
                System.out.println(ex);
            }
        }
    }
}
