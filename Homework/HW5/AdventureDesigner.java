/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #5
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 * <p>
 * AdventureDesginer guides user into making the scenes and playing the game
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class AdventureDesigner extends SceneTree {
    private static AdventureDesigner adventureDesigner = new AdventureDesigner();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        System.out.println("Creating a story...\n");
        boolean valid = false;
        String firstTitle = "";
        String firstScene = "";
        while (!valid) {
            System.out.print("Please enter a title: ");
            firstTitle = scan.nextLine();
            System.out.print("Please enter a scene: ");
            firstScene = scan.nextLine();
            String tempTitle = firstTitle.replaceAll(" ", "");
            String tempScene = firstScene.replaceAll(" ", "");
            if (tempScene.equals("") || tempTitle.equals("")) {
                System.out.println("Please enter a valid title and scene.");
                valid = false;
            } else
                valid = true;
        }
        adventureDesigner.setRoot(new SceneNode(firstTitle, firstScene));
        System.out.println("\nScene #" + getNumScenes() + " added.\n");
        while (!exit) {
            try {
                System.out.println("A) Add Scene\n" +
                        "R) Remove Scene\n" +
                        "S) Show Current Scene\n" +
                        "P) Print Adventure Tree\n" +
                        "B) Go Back A Scene\n" +
                        "F) Go Forward A Scene\n" +
                        "G) Play Game\n" +
                        "N) Print Path To Cursor\n" +
                        "M) Move scene\n" +
                        "Q) Quit");
                System.out.println("\nPlease enter a selection: ");
                String x = scan.nextLine();
                x = x.toLowerCase();
                switch (x) {
                    case "a":
                        System.out.print("Please enter a title: ");
                        String title = scan.nextLine();
                        System.out.print("Please enter a scene: ");
                        String scene = scan.nextLine();
                        String temp = title;
                        if (temp.replaceAll(" ", "").equals(""))
                            throw new IllegalArgumentException("Please enter a " +
                                    "valid title");
                        temp = scene;
                        if (temp.replaceAll(" ", "").equals(""))
                            throw new IllegalArgumentException("Please enter a " +
                                    "valid scene description");
                        adventureDesigner.addNewNode(title, scene);
                        System.out.println("\nScene #" + getNumScenes() + " added.\n");
                        break;
                    case "r":
                        System.out.println("Please enter an option: ");
                        String option = scan.nextLine();
                        adventureDesigner.removeScene(option);
                        break;
                    case "s":
                        adventureDesigner.getCursor().displayFullScene();
                        break;
                    case "p":
                        System.out.println(adventureDesigner.toString());
                        break;
                    case "b":
                        adventureDesigner.moveCursorBackwards();
                        break;
                    case "f":
                        System.out.println("Which option do you wish to go to: ");
                        String option1 = scan.nextLine();
                        adventureDesigner.moveCursorForwards(option1);
                        break;
                    case "g":
                        playGame();
                        break;
                    case "n":
                        System.out.println(adventureDesigner.getPathFromRoot());
                        break;
                    case "m":
                        System.out.println("Move current scene to: ");
                        int sceneID = scan.nextInt();
                        scan.nextLine();
                        adventureDesigner.moveScene(sceneID);
                        System.out.println("Successfully moved scene.");
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
            } catch (IllegalArgumentException | InputMismatchException |
                    NoSuchNodeException | FullSceneException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * Asks the user to select and option and prints the scene. Ends when it
     * reaches a leaf node
     */
    public static void playGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("Now beginning game...\n");
        SceneNode tempCursor = adventureDesigner.getRoot();
        while (!tempCursor.isEnding()) {
            tempCursor.displayScene();
            if (tempCursor.getRight() != null) {
                System.out.println("A) " + tempCursor.getLeft().getTitle());
                System.out.println("B) " + tempCursor.getMiddle().getTitle());
                System.out.println("C) " + tempCursor.getRight().getTitle());
            } else if (tempCursor.getMiddle() != null) {
                System.out.println("A) " + tempCursor.getLeft().getTitle());
                System.out.println("B) " + tempCursor.getMiddle().getTitle());
            } else if (tempCursor.getLeft() != null)
                System.out.println("A) " + tempCursor.getLeft().getTitle());
            System.out.println("\nPlease enter an option: ");
            String option = input.nextLine();
            boolean on = true;
            while (on) {
                switch (option.toLowerCase()) {
                    case "a":
                        tempCursor = tempCursor.getLeft();
                        on = false;
                        break;
                    case "b":
                        if (tempCursor.getMiddle() != null) {
                            tempCursor = tempCursor.getMiddle();
                            on = false;
                        }
                        break;
                    case "c":
                        if (tempCursor.getRight() != null) {
                            tempCursor = tempCursor.getRight();
                            on = false;
                        }
                        break;
                    default:
                        System.out.println("Please enter \"A\", \"B\", or \"C\"");
                        option = input.nextLine();
                        break;
                }
            }
        }
        tempCursor.displayScene();
        System.out.println("The End \n\nReturning back to creation mode...");
    }
}
