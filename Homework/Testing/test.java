import java.util.ArrayList;

public class test {
    public static void main(String[] args){
        int height = 53;
        double weight = 24.5;
        String name = "BOB          Marley   ";
        double temp = 99.7;
        int size = 24;
        String sample = String.format("%-12s%-20d%-6d%12.2f%13.2f", name, height,size, weight, temp);
        System.out.println(sample);
        System.out.println(String.format("%5s | %-18s | %3s | %-13s | %-10s | %s", "Index", "Recipient Name", "Age", "Organ Needed", "Blood Type", "Donor IDs"));
        System.out.print("rawr");
        System.out.print("help");
        System.out.println();
        System.out.println("hi");
        ArrayList<String> temp1 = new ArrayList<>();
        temp1.add("one");
        temp1.add("two");
        temp1.add("three");
        temp1.add("four");
        temp1.remove(2);
        System.out.println(temp1.get(2));
        System.out.println(name.length());
        name = name.trim();
        System.out.println(name.length());


    }

}
