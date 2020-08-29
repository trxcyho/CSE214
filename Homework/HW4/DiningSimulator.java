/**
 * DiningSimulator class does the probabilities and simulation and holds the
 * main method.
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #4
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DiningSimulator extends Restaurant {
    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private int chefs, duration, maxCustomerSize, numRestaurants;
    private int customersLost, totalServiceTime, customersServed, profit;
    private double arrivalProb;

    /**
     * Simulates the restaurants to see trends in efficiency and profit with
     * different variables
     *
     * @return the average time a customer who eats at the restaurant spends
     */
    public double simulate() {
        customersLost = 0;
        profit = 0;
        customersServed = 0;
        totalServiceTime = 0;
        int count = 1;
        for (int i = 0; i < numRestaurants; i++)
            restaurants.add(new Restaurant());
        while (count <= duration) {
            System.out.println("Time: " + count);
            for (int i = 0; i < numRestaurants; i++) {
                for (int j = 0; j < restaurants.get(i).size(); j++) {
                    Customer temp = restaurants.get(i).customers.get(j);
                    temp.setTimeToServer(temp.getTimeToServer() - 5);
                    if (temp.getTimeToServer() == 0) {
                        System.out.println("Customer #" + temp.getOrderNumber()
                          + " has enjoyed their food! $" + temp.getPriceOfFood()
                          + " profit.");
                        profit += temp.getPriceOfFood();
                        customersServed++;
                        totalServiceTime += temp.getTime();
                        restaurants.get(i).customers.remove(temp);
                    }
                }
            }

            for (int i = 0; i < numRestaurants; i++) {
                for (int j = 0; j < 3; j++) {
                    if (arrivalProb >= Math.random()) {
                        Customer customer = new Customer(getTotalCustomers()
                          + 1, count, randInt(1, 6), chefs);
                        setTotalCustomers(getTotalCustomers() + 1);
                        System.out.println("Customer #" + getTotalCustomers() +
                          " has entered Restaurant " + (i + 1) + ".");
                        restaurants.get(i).customers.add(customer);
                    }
                }
            }

            for (int i = 0; i < numRestaurants; i++) {
                int restaurantSize = restaurants.get(i).size();
                for (int j = 0; j < restaurantSize; j++) {
                    if (j >= maxCustomerSize) {
                        System.out.println("Customer #" + restaurants.get(i).
                          customers.get(maxCustomerSize).getOrderNumber() +
                          " cannot be seated! They have left the restaurant.");
                        customersLost++;
                        restaurants.get(i).customers.remove(maxCustomerSize);
                    } else if (restaurants.get(i).customers.get(j).
                      getTimeArrived() == count) {
                        System.out.println("Customer #" + restaurants.get(i).
                          customers.get(j).getOrderNumber() + " has been " +
                          "seated with order \"" + restaurants.get(i).customers.
                          get(j).getFoodExtended() + "\".");
                    }
                }
            }

            for (int i = 0; i < numRestaurants; i++) {
                System.out.println("R" + (i + 1) + ":" + restaurants.get(i).
                toString());
            }
            count++;
            System.out.println();
        }
        System.out.println("Simulation ending...");
        System.out.println();
        System.out.println("Total customer time: " + totalServiceTime);
        System.out.println("Total customers served: " + customersServed);
        double averageCustomerTime = 0;
        if (customersServed != 0)
            averageCustomerTime = Math.round((double)totalServiceTime / customersServed * 100)/100.0;
        System.out.println("Average customer time lapse: " + averageCustomerTime);
        System.out.println("Total Profit: $" + profit);
        System.out.println("Customers that left: " + customersLost);
        return averageCustomerTime;
    }

    private int randInt(int minVal, int maxVal) {
        return (int) (Math.random() * (maxVal - minVal) + minVal);
    }

    public static void main(String[] args) {
        DiningSimulator diningSimulator = new DiningSimulator();
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        System.out.println("Starting simulator...\n");
        while (!exit) {
            try {
                System.out.println("Enter the number of restaurants: ");
                diningSimulator.numRestaurants = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter the maximum number of customers a " +
                  "restaurant can serve: ");
                diningSimulator.maxCustomerSize = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter the arrival probability of a customer: ");
                diningSimulator.arrivalProb = scan.nextDouble();
                scan.nextLine();
                System.out.println("Enter the number of chefs: ");
                diningSimulator.chefs = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter the number of simulation units: ");
                diningSimulator.duration = scan.nextInt();
                scan.nextLine();

                if (diningSimulator.numRestaurants <= 0)
                    throw new IllegalArgumentException("Please enter a " +
                      "positive number of restaurants");
                if (diningSimulator.maxCustomerSize <= 0)
                    throw new IllegalArgumentException("Please enter a " +
                      "positive number for maximum numbers of customers a " +
                      "restaurant can serve.");
                if (diningSimulator.arrivalProb < 0 || diningSimulator.arrivalProb > 1)
                    throw new IllegalArgumentException("Please enter a number" +
                      " between 0 and 1 inclusive for the probability");
                if (diningSimulator.chefs <= 0)
                    throw new IllegalArgumentException("Please enter a " +
                      "positive number for number of chefs");
                if (diningSimulator.duration <= 0)
                    throw new IllegalArgumentException("Please enter a " +
                      "positive number for duration");
                diningSimulator.simulate();
                System.out.println();
                boolean choice = true;
                while(choice) {
                    System.out.println("Do you want to try another simulation? (y/n): ");
                    String answer = scan.next();
                    if (answer.toLowerCase().equals("n")) {
                        exit = true;
                        choice = false;
                    }
                    else if (answer.equalsIgnoreCase("y")) {
                        exit = false;
                        choice = false;
                    }
                    else
                        System.out.println("Please enter \"y\" for yes or \"n\" for no");
                }

            } catch (IllegalArgumentException | InputMismatchException ex) {
                System.out.println(ex);
            }
        }
        System.out.println("Program terminating normally...");
    }
}