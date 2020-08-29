/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #6
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * Item class contains the information regarding the grocery item.
 */

import java.io.Serializable;

public class Item implements Serializable {
    private String itemCode, name;
    private int qtyInStore, averageSalesPerDay, onOrder, arrivalDay;
    private double price;

    public Item() {
    }

    /**
     * Argument constructor to initialize certain data fields in the class
     * @param itemCode to be used to initialize the itemCode data field
     * @param name to be used to initialize the name data field
     * @param qtyInStore to be used to initialize the qtyInStore data field
     * @param averageSalesPerDay to be used to initialize the averageSalesPerDay data field
     * @param price to be used to initialize the price data field
     */
    public Item(String itemCode, String name, int qtyInStore, int averageSalesPerDay, double price) {
        if(itemCode.trim().equals(""))
            throw new IllegalArgumentException();
        if(name.trim().equals(""))
            throw new IllegalArgumentException();
        if(qtyInStore <= 0 || price <= 0 || averageSalesPerDay <= 0)
            throw new IllegalArgumentException("Cannot be negative");
        this.itemCode = itemCode;
        this.name = name;
        this.qtyInStore = qtyInStore;
        this.averageSalesPerDay = averageSalesPerDay;
        this.price = price;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public int getQtyInStore() {
        return qtyInStore;
    }

    public void setQtyInStore(int qtyInStore) {
        this.qtyInStore = qtyInStore;
    }

    public int getAverageSalesPerDay() {
        return averageSalesPerDay;
    }

    public int getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(int onOrder) {
        this.onOrder = onOrder;
    }

    public int getArrivalDay() {
        return arrivalDay;
    }

    public void setArrivalDay(int arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    /**
     * String representation of all the data fields in the class
     * @return the string of the item object's information
     */
    public String toString() {
        return String.format("%-12s%-20s%-6d%-8d%-8.2f%-11d%-15d", itemCode,
                name, qtyInStore, averageSalesPerDay, price, onOrder, arrivalDay);
    }
}
