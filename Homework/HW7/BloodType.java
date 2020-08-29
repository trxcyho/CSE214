/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #7
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * Class Bloodtype denotes the patients bloodtype and determines if two
 * bloodtypes are compatible with each other
 */

import java.io.Serializable;

public class BloodType implements Serializable {
    private String bloodType;

    public BloodType() {
    }

    /**
     * Argument constructor of Bloodtype
     * @param bloodType the bloodtype of either O, A, B, or AB
     */
    public BloodType(String bloodType) {
        if (bloodType.equalsIgnoreCase("o") || bloodType.equalsIgnoreCase("a")
          || bloodType.equalsIgnoreCase("ab") || bloodType.equalsIgnoreCase("b"))
            this.bloodType = bloodType.toUpperCase().trim();
        else
            throw new IllegalArgumentException("Bloodtype has to be O, A, B, or AB");
    }

    /**
     * Determines whether 2 bloodtypes are compatible
     * @param recipient the recipients bloodtype
     * @param donor the donors bloodtype
     * @return true if they are compatible, otherwise false
     */
    public static boolean isCompatible(BloodType recipient, BloodType donor) {
        boolean[][] compatibilityChart = {{true, false, false, false},
          {true, true, false, false}, {true, false, true, false}, {true, true, true, true}};
        int row = 0;
        int column = 0;
        switch (recipient.bloodType.toUpperCase()) {
            case "O":
                row = 0;
                break;
            case "A":
                row = 1;
                break;
            case "B":
                row = 2;
                break;
            case "AB":
                row = 3;
                break;
        }
        switch (donor.bloodType.toUpperCase()) {
            case "O":
                column = 0;
                break;
            case "A":
                column = 1;
                break;
            case "B":
                column = 2;
                break;
            case "AB":
                column = 3;
                break;
        }
        return compatibilityChart[row][column];
    }

    /**
     * String representation of Bloodtype class
     * @return the bloodtype
     */
    public String toString() {
        return bloodType;
    }
}
