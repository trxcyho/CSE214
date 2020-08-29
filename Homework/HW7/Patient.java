/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #7
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * Represents an active organ donor or recipient in the database
 */

import java.io.Serializable;

public class Patient extends BloodType implements Comparable, Serializable {
    private String name, organ;
    private int age, ID;
    private BloodType bloodType;
    private boolean isDonor; //true if donor false if recipient
    private int numConnections;

    public Patient() {
    }

    /**
     * an argument constructor of Patient class
     * @param name name of the new patient
     * @param organ the type of the organ the new patient
     * @param age the age of the patient
     * @param bloodType the bloodtype of the patient
     */
    public Patient(String name, String organ, int age, String bloodType) {
        if(name.trim().equals(""))
            throw new IllegalArgumentException("Please input a proper name");
        if(organ.trim().equals(""))
            throw new IllegalArgumentException("Please enter a proper organ");
        if(age <= 0)
            throw new IllegalArgumentException("Please enter a valid age");
        this.name = name.trim();
        this.organ = organ.trim();
        this.age = age;
        this.bloodType = new BloodType(bloodType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public boolean isDonor() {
        return isDonor;
    }

    public void setDonor(boolean donor) {
        isDonor = donor;
    }

    public int getNumConnections() {
        return numConnections;
    }

    public void setNumConnections(int numConnections) {
        this.numConnections = numConnections;
    }

    /**
     * Compares this Patient object to o
     *
     * @param o the other patient object to compare to
     * @return 1 if this Patient object has a greater ID number, 0 if they are
     * equivalent, and -1 if o has a greater ID number
     */
    public int compareTo(Object o) {
        //return 0 if ID matches
        //return 1 if Object o has greater ID number
        //return -1 if Object o has smaller ID number
        if (o instanceof Patient) {
            return Integer.compare(this.ID, ((Patient) o).getID());
        } else
            throw new IllegalArgumentException();
    }

    /**
     * String representation of the patient object
     *
     * @return a string representation of the patient object
     */
    public String toString() {
        return String.format(" %4d | %-18s | %3d | %-13s |      %-2s    | ", ID,
          name, age, organ, bloodType.toString());
    }
}
