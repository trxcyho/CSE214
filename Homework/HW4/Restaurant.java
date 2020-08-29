/**
 * Restaurant class holds an array list of customers
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #4
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */

import java.util.ArrayList;

public class Restaurant extends Customer {
    public Restaurant() {
    }

    ArrayList<Customer> customers = new ArrayList<>();

    /**
     * adds a customer to the end of the array list
     *
     * @param c the customer to be added
     */
    public void enqueue(Customer c) {
        customers.add(c);
    }

    /**
     * removes the customer at index 0 of the array list
     *
     * @return the first customer at index 0
     */
    public Customer dequeue() {
        return customers.remove(0);
    }

    /**
     * allows you to see the first customer of the array list without removing
     * it
     *
     * @return the customer at index 0
     */
    public Customer peek() {
        return customers.get(0);
    }

    /**
     * tells you how many customers is in the array list
     *
     * @return an int of how many customers
     */
    public int size() {
        return customers.size();
    }

    /**
     * tells you if the array list is empty
     *
     * @return true if there is no customer in the array list, false otherwise
     */
    public boolean isEmpty() {
        return customers.isEmpty();
    }

    /**
     * String representation of the contents of the array list
     *
     * @return a string with all the customers in the array list
     */
    public String toString() {
        String string = "";
        string += "{";
        if (customers.size() != 0) {
            for (int i = 0; i < customers.size() - 1; i++) {
                string += customers.get(i).toString() + ", ";
            }
            string += customers.get(customers.size() - 1).toString();
        }
        string += "}";
        return string;
    }
}
