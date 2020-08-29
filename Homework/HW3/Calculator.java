/**
 * Calculator class is the main method
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #3
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator extends HistoryStack {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        HistoryStack historyStack = new HistoryStack();
        boolean exit = false;
        System.out.println("Welcome to calculat0r. \n");
        while (!exit) {
            try {
                System.out.println("[A] Add new equation\n" +
                        "[F] Change equation from history\n" +
                        "[B] Print previous equation\n" +
                        "[P] Print full history\n" +
                        "[U] Undo\n" +
                        "[R] Redo\n" +
                        "[C] Clear history\n" +
                        "[Q] Quit");
                System.out.println("\nSelect an option: ");
                String x = scan.nextLine();
                x = x.toLowerCase();
                switch (x) {
                    case "a":
                        System.out.println("Please enter an equation (in-fix " +
                          "notation): ");
                        String inFixEquation = scan.nextLine();
                        Equation equation = new Equation(inFixEquation);
                        if (equation.isBalanced())
                            System.out.println("The equation is balanced and " +
                              "the answer is " + String.format("%.03f", equation
                              .getAnswer()));
                        else
                            System.out.println("The equation is not balanced.");
                        historyStack.push(equation);
                        break;
                    case "f":
                        System.out.println("Which equation would you like to " +
                          "change?");
                        int position = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Equation at position " + position +
                          ": " + historyStack.getEquation(position).
                          getEquation());
                        boolean makeChanges = true;
                        Equation temp = new Equation(historyStack.
                          getEquation(position).getEquation());
                        while (makeChanges) {
                            try {
                                System.out.println("What would you like to do to " +
                                  "the equation (Replace / remove / add)?");
                                String option = scan.nextLine();
                                option = option.toLowerCase();
                                switch (option) {
                                    case "replace": {
                                        System.out.println("What position would you " +
                                                "like to change?");
                                        int index = scan.nextInt();
                                        scan.nextLine();
                                        System.out.println("What would you like to " +
                                                "replace it with?");
                                        String replace = scan.nextLine();
                                        temp.setEquation(temp.getEquation().substring(0,
                                                index - 1) + replace + temp.getEquation().
                                                substring(index));
                                        break;
                                    }
                                    case "remove": {
                                        System.out.println("What position would you " +
                                                "like to remove?");
                                        int index = scan.nextInt();
                                        scan.nextLine();
                                        temp.setEquation(temp.getEquation().substring(0,
                                                index - 1) + temp.getEquation().substring(
                                                index));
                                        break;
                                    }
                                    case "add": {
                                        System.out.println("What position would you " +
                                                "like to add something?");
                                        int index = scan.nextInt();
                                        scan.nextLine();
                                        System.out.println("What would you like to " +
                                                "add?");
                                        String replace = scan.nextLine();
                                        temp.setEquation(temp.getEquation().substring(0,
                                                index - 1) + replace + temp.getEquation().
                                                substring(index - 1));
                                        break;
                                    }
                                    default:
                                        System.out.println("Invalid Input");
                                        break;
                                }
                                System.out.println("Equation: " + temp.getEquation(
                                ));
                                System.out.println("Would you like to make any " +
                                        "more changes?");
                                String ans = scan.nextLine();
                                ans = ans.toLowerCase();
                                if (ans.equals("y") || ans.equals("yes")) {
                                } else if (ans.equals("n") || ans.equals("no"))
                                    makeChanges = false;
                                else
                                    System.out.println("Enter a valid input");
                            }
                            catch (InputMismatchException e) {
                                System.out.println(e);
                            }
                        }
                        Equation newEquation = new Equation(temp.getEquation());
                        historyStack.push(newEquation);
                        if(newEquation.isBalanced())
                            System.out.println("The equation is balanced and " +
                              "the answer is: " + String.format("%.03f",
                              newEquation.getAnswer()));
                        else
                            System.out.println("The equation is not balanced " +
                              "and the answer is: " + String.format("%.03f",
                              newEquation.getAnswer()));
                        break;
                    case "b":
                        System.out.println(String.format("%-4s%-35s%-35s%-3" +
                          "5s%-18s%-15s%13s", "#", "Equation", "Pre-Fix",
                            "Post-Fix", "Answer", "Binary", "Hexadecimal") +
                            "\n");
                        for (int i = 0; i < 165; i++)
                            System.out.print("-");
                        System.out.println();
                        System.out.printf("%-4s%s", historyStack.size(),
                          historyStack.peek().toString());
                        break;
                    case "p":
                        System.out.println(historyStack.toString());
                        break;
                    case "u":
                        String undoneEquation = historyStack.peek().getEquation(
                          );
                        historyStack.undo();
                        System.out.println("Equation '" + undoneEquation +
                          "' undone.");
                        break;
                    case "r":
                        historyStack.redo();
                        System.out.println("Redoing equation '" + historyStack.
                          peek().getEquation() + "'.");
                        break;
                    case "c":
                        System.out.println("Resetting calculator.");
                        historyStack.empty();
                        break;
                    case "q":
                        System.out.println("Program terminating normally " +
                                "...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            } catch (IllegalArgumentException | InputMismatchException ex) {
                System.out.println(ex);
            }
        }
    }
}
