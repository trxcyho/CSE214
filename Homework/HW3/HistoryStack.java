/**
 * history stack class holds the functions for the calculator
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #3
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */

import java.util.Stack;

public class HistoryStack extends Equation {
    public HistoryStack() {
    }

    Stack<Equation> history = new Stack<>();
    Stack<Equation> backup = new Stack<>();

    /**
     * pushes the equation into the history stack
     *
     * @param newEquation the equation that is being put into the stack
     */
    public void push(Equation newEquation) {
        history.push(newEquation);
    }

    /**
     * takes the top equation out of history stack
     *
     * @return the top equation in the history stack
     */
    public Equation pop() {
        if (history.isEmpty())
            throw new IllegalArgumentException("There is no equation");
        return history.pop();
    }

    /**
     * removes the last Equation from the top of the history stack and putting
     * it into backup stack
     */
    public void undo() {
        if (history.isEmpty())
            throw new IllegalArgumentException("There is nothing to be undone");
        else
            backup.push(history.pop());
    }

    /**
     * puts the last undone equation from backup stack into the history stack
     */
    public void redo() {
        if (backup.isEmpty())
            throw new IllegalArgumentException("There is nothing to redo");
        else
            history.push(backup.pop());
    }

    /**
     * the amount of equations in history stack
     *
     * @return the amount of equations
     */
    public int size() {
        return history.size();
    }

    /**
     * Gets the equation at a certain position
     *
     * @param position the position to get the equation you want
     * @return the equation at that position in the history stack
     */
    public Equation getEquation(int position) {
        if (position > history.size() || position <= 0)
            throw new IllegalArgumentException("That position is invalid");
        else
            return history.get(position - 1);
    }

    /**
     * look at the top equation in history stack
     *
     * @return the top equation of the stack without removing it from the stack
     */
    public Equation peek() {
        if (history.isEmpty())
            throw new IllegalArgumentException("There is no equation in the " +
                    "stack");
        else
            return history.peek();
    }

    /**
     * a string representation of the class
     *
     * @return a table of the contents of the class
     */
    public String toString() {
        String answer = "";
        answer += String.format("%-4s%-35s%-35s%-35s%-15s%-15s%13s", "#",
          "Equation", "Pre-Fix", "Post-Fix", "Answer", "Binary", "Hexadecimal")
          + "\n";
        for (int i = 0; i < 165; i++)
            answer += "-";
        answer += "\n";
        int size = size();
        for (int i = history.size() - 1; i >= 0; i--) {
            answer += String.format("%-4s%s", size, history.get(i).toString());
            size--;
        }
        return answer;
    }

    /**
     * clears and makes history and backup stack empty
     */
    public void empty() {
        while(!history.isEmpty())
            history.pop();
        while(!backup.isEmpty())
            backup.pop();
    }
}