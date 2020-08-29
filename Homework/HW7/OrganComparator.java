/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #7
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * OrganComparator compares the Patients organs
 */

import java.util.Comparator;

public class OrganComparator implements Comparator<Patient> {

    /**
     * Compares the Organ of two Patients
     *
     * @param o1 the first patient to be compared
     * @param o2 the second patient to be compared
     * @return 0 if the organs are the same, 1 if Patient o1 has a larger organ
     * value, -1 otherwise
     */
    public int compare(Patient o1, Patient o2) {
        return o1.getOrgan().compareToIgnoreCase(o2.getOrgan());
    }
}
