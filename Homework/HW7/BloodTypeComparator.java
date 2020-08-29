/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #7
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * BloodTypeComparator compares the bloodtype of two Patient objects
 */

import java.util.Comparator;

public class BloodTypeComparator implements Comparator<Patient> {
    /**
     * Compares the Bloodtype of both patients
     *
     * @param o1 the first patient object to be compared
     * @param o2 the second patient object to be compared
     * @return 0 if bloodtypes are the same, 1 if the first patient has a bigger
     * bloodtype, -1 otherwise
     */
    public int compare(Patient o1, Patient o2) {
        return o1.getBloodType().toString().compareToIgnoreCase(o2.getBloodType().toString());
    }
}
