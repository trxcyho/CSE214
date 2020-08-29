/**
 * Equation Stack class has all the methods that the stack API has
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #3
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */

import java.util.Stack;

public class EquationStack {
    private Stack<String> stack = new Stack<>();

    public EquationStack() {
    }

    /**
     * Pushes the string into the stack
     *
     * @param s the string that is being put into the stack
     */
    public void push(String s) {
        stack.push(s);
    }

    /**
     * Returns the top string from the stack
     *
     * @return a string from the stack
     */
    public String pop() {
        return stack.pop();
    }

    /**
     * checks if the stack is empty
     *
     * @return a boolean: true if stack is empty false otherwise
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * tells you how many contents are inside the stack
     *
     * @return an int that has the amount of strings in the stack
     */
    public int size() {
        return stack.size();
    }

    /**
     * allows you to see the contents of the top string in the stack
     *
     * @return the String at the top of the stack without taking it out of the
     * stack
     */
    public String peek() {
        return stack.peek();
    }

}
