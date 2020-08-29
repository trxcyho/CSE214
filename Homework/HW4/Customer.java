/**
 * Customer class holds the details for each customer
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #4
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */
public class Customer {
    private static int totalCustomers;
    private int orderNumber;
    private int priceOfFood;
    private int timeArrived;
    private int timeToServer;
    private int time;
    private String food, foodExtended;

    public static int getTotalCustomers() {
        return totalCustomers;
    }

    public static void setTotalCustomers(int totalCustomers) {
        Customer.totalCustomers = totalCustomers;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPriceOfFood() {
        return priceOfFood;
    }

    public void setPriceOfFood(int priceOfFood) {
        this.priceOfFood = priceOfFood;
    }

    public int getTimeArrived() {
        return timeArrived;
    }

    public void setTimeArrived(int timeArrived) {
        this.timeArrived = timeArrived;
    }

    public int getTimeToServer() {
        return timeToServer;
    }

    public void setTimeToServer(int timeToServer) {
        this.timeToServer = timeToServer;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFoodExtended() {
        return foodExtended;
    }

    public void setFoodExtended(String foodExtended) {
        this.foodExtended = foodExtended;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Customer() {
    }

    /**
     * Argument constructor for Customer class that assigns a random food to the
     * customer and calculates the time to serve the food
     *
     * @param orderNumber sets the orderNumber specific to each customer
     * @param timeArrived sets the time the customer arrived during each
     *                    stimulation
     * @param foodOrder   corresponds to a certain food
     * @param chefs       is used to calculate the amount of time for the
     *                    customer to finish the food
     */
    public Customer(int orderNumber, int timeArrived, int foodOrder, int chefs) {
        int additionalTime;
        if (chefs >= 5)
            chefs = 5;
        additionalTime = -(chefs - 3) * 5;

        this.orderNumber = orderNumber;
        this.timeArrived = timeArrived;
        switch (foodOrder) {
            case 1:
                food = "S";
                foodExtended = "Steak";
                timeToServer = 45 + additionalTime;
                priceOfFood = 25;
                break;
            case 2:
                food = "CW";
                foodExtended = "Chicken Wings";
                timeToServer = 45 + additionalTime;
                priceOfFood = 20;
                break;
            case 3:
                food = "CT";
                foodExtended = "Chicken Tenders";
                timeToServer = 40 + additionalTime;
                priceOfFood = 10;
                break;
            case 4:
                food = "C";
                foodExtended = "Cheeseburger";
                timeToServer = 40 + additionalTime;
                priceOfFood = 15;
                break;
            case 5:
                food = "GC";
                foodExtended = "Grilled Cheese";
                timeToServer = 30 + additionalTime;
                priceOfFood = 10;
                break;
        }
        time = timeToServer;
    }

    /**
     * A string representation of the customer
     *
     * @return a string that contains the order number, the food, and how much
     * time is left to be finished
     */
    public String toString() {
        return "[#" + orderNumber + ", " + food + ", " + timeToServer + "min.]";
    }
}
