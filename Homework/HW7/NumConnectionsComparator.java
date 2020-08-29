/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #7
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * NumConnectionsComparator compares the number of connections between Patient
 * objects
 */

import java.util.Comparator;

public class NumConnectionsComparator implements Comparator<Patient> {

    /**
     * Compares the number of connections between two patients
     *
     * @param o1 the first patient to be compared
     * @param o2 the second patient to be compared
     * @return 0 if they have the same amount of connections, 1 if Patient o1
     * has more connections and -1 otherwise
     */
    public int compare(Patient o1, Patient o2) {
        return Integer.compare(o1.getNumConnections(), o2.getNumConnections());
    }
}
