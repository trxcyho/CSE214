/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #6
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * HashedGrocery contains the methods that let user perform functions on the
 * inventory of the grocery store.
 */

import java.io.*;
import java.util.Hashtable;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HashedGrocery implements Serializable {
    private int businessDay = 1;
    private Hashtable<String, Item> hashtable = new Hashtable<>();

    public HashedGrocery() {
    }

    public int getBusinessDay() {
        return businessDay;
    }

    public void setBusinessDay(int businessDay) {
        this.businessDay = businessDay;
    }

    public Hashtable<String, Item> getHashtable() {
        return hashtable;
    }

    public void setHashtable(Hashtable<String, Item> hashtable) {
        this.hashtable = hashtable;
    }

    /**
     * Adds item to the hash table
     * @param item the item to be added to the hashtable
     */
    public void addItem(Item item) {
        if (hashtable.containsKey(item.getItemCode()))
            System.out.println(item.getItemCode() + ": Cannot add item as " +
              "item code already exists.");
        else {
            hashtable.put(item.getItemCode(), item);
            System.out.println(item.getItemCode() + ": " + item.getName() +
              " is added to inventory.");
        }
    }

    /**
     * Changes the qtyInStore amount of item by adjustByQty
     * @param item
     * @param adjustByQty
     */
    public void updateItem(Item item, int adjustByQty) {
        int newQty = item.getQtyInStore() - adjustByQty;
        item.setQtyInStore(newQty);
    }

    /**
     * Adds all the items present in the text file
     * @param filename the file with JSON information for one or more Item objects
     * @throws IOException thrown if there is something wrong with the file
     * @throws ParseException thrown if there is something wrong with converting the file
     */
    public void addItemCatalog(String filename) throws IOException, ParseException {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        JSONParser parser = new JSONParser();
        JSONArray items = (JSONArray) parser.parse(isr);
        for (Object item : items) {
            JSONObject obj = (JSONObject) item;
            String itemCode = (String) obj.get("itemCode");
            String itemName = (String) obj.get("itemName");
            int itemAvgSales = Integer.parseInt((String) obj.get("avgSales"));
            int itemQtyInStore = Integer.parseInt((String) obj.get("qtyInStore"));
            double itemPrice = Double.parseDouble((String) obj.get("price"));
            int itemAmtOnOrder = Integer.parseInt((String) obj.get("amtOnOrder"));
            Item newItem = new Item(itemCode, itemName, itemQtyInStore, itemAvgSales, itemPrice);
            newItem.setOnOrder(itemAmtOnOrder);
            addItem(newItem);
        }
    }

    /**
     * Processes filename to see which items have been sold that day
     * @param filename JSON information for one or more sales
     * @throws IOException thrown if there is something wrong with the file
     * @throws ParseException thrown if there is something wrong with converting the file
     */
    public void processSales(String filename) throws IOException, ParseException {
        FileInputStream stream = new FileInputStream(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        JSONParser parser = new JSONParser();
        JSONArray sales = (JSONArray) parser.parse(inputStreamReader);
        for (Object sale : sales) {
            JSONObject obj = (JSONObject) sale;
            String itemCode = (String) obj.get("itemCode");
            int itemQty = Integer.parseInt((String) obj.get("qtySold"));
            if (!hashtable.containsKey(itemCode))
                System.out.println(itemCode + ": Cannot buy as it is not " +
                  "in the grocery store.");
            else {
                Item temp = hashtable.get(itemCode);
                if (itemQty > temp.getQtyInStore())
                    System.out.println(itemCode + ":  Not enough stock for " +
                      "sale. Not updated.");
                else {
                    updateItem(hashtable.get(itemCode), itemQty);
                    //enough stock for next 3 days
                    if (minQtyInStock(temp) <= hashtable.get(itemCode).
                      getQtyInStore())
                        System.out.println(temp.getItemCode() + ": " + itemQty +
                          " units of " + temp.getName() + " are sold.");
                    else {
                        //checks if order isn't placed
                        if (temp.getOnOrder() == 0) {
                            placeOrder(hashtable.get(itemCode));
                            System.out.println(temp.getItemCode() + ": " + itemQty
                              + " units of " + temp.getName() + " are sold. " +
                              "Order has been placed for " + amountToOrder(temp)
                              + " more units.");
                        }
                    }
                }
            }
        }
    }

    /**
     * Places an order for an item
     * @param item the item which needs more quantity
     */
    private void placeOrder(Item item) {
        item.setOnOrder(amountToOrder(item));
        item.setArrivalDay(businessDay + 3);
    }

    /**
     * Returns the amount of order to be placed for the item
     * @param item the item that sees if an order needs to be placed
     * @return the amount to order
     */
    private int amountToOrder(Item item) {
        return 2 * item.getAverageSalesPerDay();
    }

    /**
     * Determines the min qty in stock before you place an order
     * @param item the item to be determined the min qty
     * @return the min qty of that stock before placing an order
     */
    private int minQtyInStock(Item item) {
        return 3 * item.getAverageSalesPerDay();
    }

    /**
     * Moves to the next business day and checks if any orders arrived
     */
    public void nextBusinessDay() {
        System.out.println("Advancing business day...");
        businessDay = businessDay + 1;
        System.out.println("Business Day " + businessDay);
        Set<String> setOfItemCodes = hashtable.keySet();
        boolean arrivals = false;
        for (String key : setOfItemCodes) {
            //todo: for loop can spit key to be null??
            if (hashtable.get(key).getArrivalDay() == businessDay) {
                if (!arrivals)
                    System.out.println("Orders have arrived for: ");
                arrivals = true;
                hashtable.get(key).setQtyInStore(hashtable.get(key).getQtyInStore()
                  + hashtable.get(key).getOnOrder());
                System.out.println(hashtable.get(key).getItemCode() + ": " +
                  hashtable.get(key).getOnOrder() + " units of " + hashtable.
                  get(key).getName() + ".");
                hashtable.get(key).setOnOrder(0);
                hashtable.get(key).setArrivalDay(0);
            }
        }
        if (!arrivals)
            System.out.println("No orders have arrived.\n");
    }

    /**
     * A string representation of all the items in the hashtable
     * @return a string representation of all the items
     */
    public String toString() {
        String table = "";
        table += String.format("%-12s%-20s%-6s%-8s%-8s%-11s%-15s", "Item code",
                "Name", "Qty", "AvgSales", "Price", "OnOrder", "ArrOnBusDay");
        table += "\n";
        for (int i = 0; i < 80; i++)
            table += "-";
        table += "\n";
        Set<String> setOfItemCodes = hashtable.keySet();
        for (String key : setOfItemCodes)
            table = table + hashtable.get(key).toString() + "\n";
        return table;
    }
}
